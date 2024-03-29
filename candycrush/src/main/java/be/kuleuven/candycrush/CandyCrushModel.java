package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandyCrushModel {
    public String playerName;
    public int score;
    public BoardSize boardSize;
    public Board<Candy> candyBoard;

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


    public boolean compareCandyAtPosition(Candy neighborCandy, Candy candyToCheck) {
        return neighborCandy.equals(candyToCheck);
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize.rows() && col >= 0 && col < boardSize.columns();
    }
}
