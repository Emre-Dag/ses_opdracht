package be.kuleuven;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // grid
        Integer[] gridArray = {
                1, 0, 1, 2,
                1, 1, 0, 2,
                0, 1, 3, 3,
                0, 3, 2, 1
        };

        // array naar Iterable List
        Iterable<Integer> grid = Arrays.asList(gridArray);

        // grid width en height
        int width = 4;
        int height = 4;

        // Index to check
        int indexToCheck = 5;

        Iterable<Integer> result = getSameNeighboursIds.getSameNeighboursIds(grid, width, height, indexToCheck);
        System.out.println("Same neighbors at index " + indexToCheck + ": " + result);
    }
}
