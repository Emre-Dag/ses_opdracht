package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandyCrushModel {
    public String playerName;
    public int score;
    private Iterable<Integer> grid;
    public BoardSize boardSize;
    public CandyCrushModel(BoardSize boardSize) {
        this.boardSize = boardSize;
        this.playerName="Player1";
        this.score = 0;
        this.grid = generateRandomGrid();
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

    public Iterable<Integer> getGrid() {
        return grid;
    }

    public void updateGrid(List<Integer> newGrid) {
        if (newGrid == null) {
            throw new NullPointerException("New grid cannot be null");
        }
        grid = newGrid;
    }


    public Iterable<Integer> generateRandomGrid() {
        int n = boardSize.rows() * boardSize.columns();
        Random rd = new Random();
        Integer[] array = new Integer[n];
        int min = 0;
        int max = 3;

        for (int i = 0; i < array.length; i++) {
            array[i] = rd.nextInt(max - min + 1) + min;
        }

        return Arrays.asList(array);
    }
    public int getGridValue(Iterable<Integer> grid, Position position) {
        // Converteer Iterable<Integer> naar List<Integer>
        List<Integer> gridList = new ArrayList<>();
        grid.forEach(gridList::add);

        return gridList.get(position.toIndex());
    }
    public void updateGridValue(Position position, int value) {
        // Converteer Iterable<Integer> naar List<Integer>
        List<Integer> gridList = new ArrayList<>();
        grid.forEach(gridList::add);

        // Update de waarde op de opgegeven positie
        gridList.set(position.toIndex(), value);

        // Update het grid in het model
        updateGrid(gridList);
    }
    // Methode om een willekeurig Candy-object aan te maken
    public Candy createRandomCandy() {
        Random random = new Random();
        int candyType = random.nextInt(6); // 0-3 voor NormalCandy, 4-5 voor speciale snoepjes

        return switch (candyType) {
            case 0, 1, 2, 3 ->
                // Maak een NormalCandy met een willekeurige kleur (0, 1, 2, of 3)
                    new NormalCandy(random.nextInt(4));
            case 4 -> new ChocoCrunch();
            case 5 -> new CaramelBlast();
            case 6 -> new LemonDrop();
            case 7 -> new BerryBurst();
            default -> throw new IllegalStateException("Unexpected candy type: " + candyType);
        };
    }
}
