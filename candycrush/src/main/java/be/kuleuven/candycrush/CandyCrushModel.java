package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class CandyCrushModel {
    public String playerName;
    public int score;
    public BoardSize boardSize;
    public Board<Candy> candyBoard;

    public int maxScore;
    public List<List<Position>> bestSequence;

    public CandyCrushModel(BoardSize boardSize) {
        this.boardSize = boardSize;
        this.candyBoard = new Board<>(boardSize);
        this.playerName = "Player1";
        this.score = 0;
        generateRandomGrid();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int value) {
        score += value;
    }

    public Board<Candy> getCandyBoard() {
        return candyBoard;
    }
    public void generateRandomGrid() {
        candyBoard.fill(this::createRandomCandy);
    }

    public Candy getCandyAt(Position position) {
        return candyBoard.getCellAt(position);
    }

    public void updateCandyAt(Position position, Candy candy) {
        candyBoard.replaceCellAt(position, candy);
    }

    // Method to create a random Candy object
    public Candy createRandomCandy(Position position) {
        Random random = new Random();
        int candyType = random.nextInt(8); // 0-3 for NormalCandy, 4-7 for special candies

        return switch (candyType) {
            case 0, 1, 2, 3 ->
                // Create a NormalCandy with a random color (0, 1, 2, or 3)
                    new NormalCandy(random.nextInt(4));
            case 4 -> new ChocoCrunch();
            case 5 -> new CaramelBlast();
            case 6 -> new LemonDrop();
            case 7 -> new BerryBurst();
            default -> throw new IllegalStateException("Unexpected candy type: " + candyType);
        };
    }

    // Methode om dezelfde buurposities op te halen voor een gegeven positie
    public Iterable<Position> getSameNeighbourPositions(Position position) {
        List<Position> result = new ArrayList<>();

        // Iterate over the neighbor positions of the given position
        for (Position neighborPosition : position.neighborPositions()) {
            // Retrieve the candy at the neighbor position
            Candy neighborCandy = candyBoard.getCellAt(neighborPosition);

            // Add the neighbor position to the result if it's valid and has the same candy
            if (isValidPosition(neighborPosition.row(), neighborPosition.column()) && compareCandyAtPosition(neighborCandy, candyBoard.getCellAt(position))) {
                result.add(neighborPosition);
            }
        }

        return result;
    }


    public boolean compareCandyAtPosition(Candy candy, Candy neighborCandy) {
        // Als beide snoepjes null zijn, beschouwen we ze als gelijk
        if (candy == null && neighborCandy == null) return false;
        // Als slechts één van de snoepjes null is, beschouwen we ze als verschillend
        if (candy == null || neighborCandy == null) return false;
        // Vergelijk de snoepjes normaal als ze beide niet null zijn
        return candy.equals(neighborCandy);
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize.rows() && col >= 0 && col < boardSize.columns();
    }

    public boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions) {
        return positions.limit(2).allMatch(pos -> compareCandyAtPosition(candy, getCandyAt(pos)));
    }
    public Stream<Position> horizontalStartingPositions() {
        return boardSize.positions().stream()
                .filter(pos -> !firstTwoHaveCandy(getCandyAt(pos), pos.walkLeft())||pos.column()==0);
    }

    public Stream<Position> verticalStartingPositions() {
        return boardSize.positions().stream()
                .filter(pos -> !firstTwoHaveCandy(getCandyAt(pos), pos.walkUp())||pos.row()==0);
    }
    public List<Position> longestMatchToRight(Position pos) {
        return pos.walkRight().takeWhile(nextPos -> compareCandyAtPosition(getCandyAt(nextPos), getCandyAt(pos)))
                .collect(Collectors.toList());
    }

    public List<Position> longestMatchDown(Position pos) {
        return pos.walkDown().takeWhile(nextPos -> compareCandyAtPosition(getCandyAt(nextPos), getCandyAt(pos)))
                .collect(Collectors.toList());
    }

    public Set<List<Position>> findAllMatches() {
        Set<List<Position>> matches = new HashSet<>();

        // Horizontale matches
        Stream<Position> horizontalStartingPositions = horizontalStartingPositions();
        Stream<List<Position>> horizontalMatches = horizontalStartingPositions
                .map(this::longestMatchToRight)
                .filter(match -> match.size() >= 3); // filter matches with more than 3 candies
        matches.addAll(horizontalMatches.collect(Collectors.toSet()));

        // Verticale matches
        Stream<Position> verticalStartingPositions = verticalStartingPositions();
        Stream<List<Position>> verticalMatches = verticalStartingPositions
                .map(this::longestMatchDown)
                .filter(match -> match.size() >= 3); // filter matches with more than 3 candies
        matches.addAll(verticalMatches.collect(Collectors.toSet()));

        return matches;
    }

    public void clearMatch(List<Position> match) {
        for (Position pos : match) {
            // Verwijder het snoepje op de huidige positie
            updateCandyAt(pos, null);

            // Loop door de buurposities en roep de methode recursief aan voor elke buurpositie
            for (Position neighbor : pos.neighborPositions()) {
                // Controleer of de buurpositie een snoepje bevat en of het overeenkomt met het snoepje op de huidige positie
                if (neighbor != null && getCandyAt(neighbor) != null && compareCandyAtPosition(getCandyAt(neighbor), getCandyAt(pos))) {
                    // Roep de methode recursief aan voor de buurpositie
                    clearMatch(List.of(neighbor));
                }
            }
        }
    }

    public void fallDownTo(Position pos) {
        int row = pos.row();
        int col = pos.column();

        // Check if we are on the top row, in which case we cannot fall down further
        if (row == 0) {
            return;
        }

        // Get the candy at the current position
        Candy candy = candyBoard.getCellAt(pos);

        // Check if the cell above is empty
        if (candy == null) {
            // Move the candy from the cell above, if it exists
            for (int r = row - 1; r >= 0; r--) {
                Candy candyAbove = candyBoard.getCellAt(new Position(r, col, boardSize));
                if (candyAbove != null) {
                    candyBoard.replaceCellAt(new Position(r, col, boardSize), null);
                    candyBoard.replaceCellAt(new Position(row, col, boardSize), candyAbove);
                    break;
                }
            }
        }

        // Recursively call fallDownTo for the cell above
        fallDownTo(new Position(row - 1, col, boardSize));
    }

    public boolean updateBoard() {
        boolean matchRemoved = false;

        // Find all matches on the board
        Set<List<Position>> matches = findAllMatches();

        // If there are matches, clear them and let the candies fall down
        if (!matches.isEmpty()) {
            matchRemoved = true;
            for (List<Position> match : matches) {
                // Calculate the score gained from the current match
                int matchScore = calculateMatchScore(match);
                // Update the total score
                updateScore(matchScore);
                // Clear the current match
                clearMatch(match);
            }
            // After clearing matches, let candies fall down
            for (int col = 0; col < boardSize.columns(); col++) {
                for (int row = boardSize.rows() - 1; row >= 0; row--) {
                    fallDownTo(new Position(row, col, boardSize));
                }
            }
            // Recursively update the board until no more matches are found
            matchRemoved |= updateBoard();
        }

        return matchRemoved;
    }

    private int calculateMatchScore(List<Position> match) {
        return match.size() ;
    }



    public void printBoard() {
        int rows = candyBoard.boardSize.rows();
        int cols = candyBoard.boardSize.columns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Position currentPosition = new Position(i, j, boardSize);
                Candy currentCandy = candyBoard.getCellAt(currentPosition);
                switch (currentCandy) {
                    case NormalCandy normalCandy -> System.out.print(normalCandy.color() + " ");
                    case ChocoCrunch chocoCrunch -> System.out.print("5 ");
                    case CaramelBlast caramelBlast -> System.out.print("6 ");
                    case LemonDrop lemonDrop -> System.out.print("7 ");
                    case BerryBurst berryBurst -> System.out.print("8 ");
                    case null, default -> System.out.print("n ");
                }
            }
            System.out.println();
        }
    }

    public List<List<Position>> maximizeScore() {
        maxScore = 0;
        bestSequence = new ArrayList<>();
        List<List<Position>> currentSequence = new ArrayList<>();
        updateBoard(); // Update the board initially
        backtrack(currentSequence);
        return bestSequence;
    }

    private void backtrack(List<List<Position>> currentSequence) {
        //printBoard();
        //System.out.println();
        // Base case: If there are no more valid switches or the board is empty after updates, return
        if ((noValidSwitches()&& findAllMatches().isEmpty()) || boardIsEmpty()) {
            int currentScore = getScore();
            if (currentScore > maxScore) {
                maxScore = currentScore;
                bestSequence = currentSequence;
                //System.out.println("New best sequence found: " + bestSequence);
            }
            return;
        }

        // For horizontal swaps
        for (int row = 0; row < boardSize.rows(); row++) {
            for (int col = 0; col < boardSize.columns() - 1; col++) {
                if(matchAfterSwitch(row, col, row, col + 1)) {
                    // Perform the horizontal swap
                    swapCandies(row, col, row, col + 1);
                    currentSequence.add(List.of(new Position(row, col, boardSize), new Position(row, col + 1, boardSize)));
                    // Recursively call backtrack
                    //System.out.println("Performing horizontal swap: (" + row + "," + col + ") <-> (" + row + "," + (col + 1) + ")");
                    // Create a copy of the model
                    CandyCrushModel copyModel = new CandyCrushModel(boardSize);
                    candyBoard.copyTo(copyModel.candyBoard);
                    copyModel.score = score;

                    updateBoard(); // Update the board after the swap
                    backtrack(new ArrayList<>(currentSequence));
                    copyModel.candyBoard.copyTo(this.candyBoard);
                    score = copyModel.score;
                    // Undo the horizontal swap
                    swapCandies(row, col, row, col + 1);
                    currentSequence.removeLast();
                    //currentSequence.removeLast();
                }
            }
        }
        // For vertical swaps
        for (int row = 0; row < boardSize.rows() - 1; row++) {
            for (int col = 0; col < boardSize.columns(); col++) {
                if(matchAfterSwitch(row, col, row + 1, col)) {
                    // Perform the vertical swap
                    swapCandies(row, col, row + 1, col);
                    currentSequence.add(List.of(new Position(row, col, boardSize), new Position(row + 1, col, boardSize)));

                    // Recursively call backtrack
                    // Create a copy of the model
                    CandyCrushModel copyModel = new CandyCrushModel(boardSize);
                    candyBoard.copyTo(copyModel.candyBoard);
                    copyModel.score = score;

                    updateBoard(); // Update the board after the swap
                    backtrack(new ArrayList<>(currentSequence));

                    copyModel.candyBoard.copyTo(this.candyBoard);
                    score = copyModel.score;

                    // Undo the vertical swap
                    swapCandies(row, col, row + 1, col);
                    currentSequence.removeLast();
                    //currentSequence.removeLast();
                }
            }
        }
    }

    private boolean boardIsEmpty() {
        for (int row = 0; row < boardSize.rows(); row++) {
            for (int col = 0; col < boardSize.columns(); col++) {
                if (getCandyAt(new Position(row, col, boardSize)) != null) {
                    return false; // Found a candy, board is not empty
                }
            }
        }
        return true; // No candies found, board is empty
    }
    boolean noValidSwitches() {
        for (int row = 0; row < boardSize.rows(); row++) {
            for (int col = 0; col < boardSize.columns()-1; col++){
                if (matchAfterSwitch(row,col,row,col+1))
                {
                    return false;
                }
            }
        }
        for (int row = 0; row < boardSize.rows()-1; row++) {
            for (int col = 0; col < boardSize.columns(); col++){
                if (matchAfterSwitch(row,col,row+1,col))
                {
                    return false;
                }
            }
        }
        // If no match is found after all possible swaps, return true indicating no valid switches
        return true;
    }

    private boolean matchAfterSwitch(int row1, int col1, int row2, int col2) {
        // Create a copy of the model
        CandyCrushModel copyModel = new CandyCrushModel(boardSize);
        candyBoard.copyTo(copyModel.candyBoard);
        copyModel.score = score;
        // Perform the swap in the copied model
        copyModel.swapCandies(row1, col1, row2, col2);
        // Find matches on the current board state
        Set<List<Position>> matches = copyModel.findAllMatches();
        // Return true if matches are found after the swap, false otherwise
        return !matches.isEmpty();
    }

    public void swapCandies(int row1, int col1, int row2, int col2) {
        // Check if the positions are valid
        if (!isValidPosition(row1, col1) || !isValidPosition(row2, col2)) {
            return;
        }

        // Get the candies from the specified positions
        Candy candy1 = getCandyAt(new Position(row1, col1, boardSize));
        Candy candy2 = getCandyAt(new Position(row2, col2, boardSize));

        // Swap the candies by replacing them at their positions
        updateCandyAt(new Position(row1, col1, boardSize), candy2);
        updateCandyAt(new Position(row2, col2, boardSize), candy1);
    }
}
