package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandyCrushModel {
    public String playerName;
    public int score;
    private Iterable<Integer> grid;

    public CandyCrushModel() {
        this.playerName="Player1";
        this.score = 0;
        this.grid = generateRandomGrid(4, 4);
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


    public Iterable<Integer> generateRandomGrid(int rows, int cols) {
        int n = rows * cols;
        Random rd = new Random();
        Integer[] array = new Integer[n];
        int min = 0;
        int max = 3;

        for (int i = 0; i < array.length; i++) {
            array[i] = rd.nextInt(max - min + 1) + min;
        }

        return Arrays.asList(array);
    }
    public int getGridValue(Iterable<Integer> grid, int width, int row, int col) {
        // Convert Iterable<Integer> to List<Integer>
        List<Integer> gridList = new ArrayList<>();
        grid.forEach(gridList::add);

        return gridList.get(row * width + col);
    }
    public void updateGridValue(int index, int value) {
        // Convert Iterable<Integer> to List<Integer>
        List<Integer> gridList = new ArrayList<>();
        grid.forEach(gridList::add);

        // Update the value at the specified index
        gridList.set(index, value);

        // Update the grid in the model
        updateGrid(gridList);
    }

}
