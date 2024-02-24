package be.kuleuven;
import java.util.ArrayList;
import java.util.List;
public class getSameNeighboursIds {

    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid, int width, int height, int indexToCheck)
    {
        List<Integer> result = new ArrayList<>();
        int row = indexToCheck / width;
        int col = indexToCheck % width;
        List<Integer> gridList = (List<Integer>) grid;
        int valueToCheck = gridList.get(indexToCheck);

        // linksboven waarde
        if (indexcheck(width, height, row-1, col-1)) {
            int index = bepaalindex(row-1,width,col-1);
            if (vergelijkwaarde(gridList,width, row-1, col-1, valueToCheck)) {
                result.add(index);
            }
        }

        // bovenste waarde
        if (indexcheck(width, height, row-1, col)) {
            int index = bepaalindex(row-1,width,col);
            if (vergelijkwaarde(gridList,width, row-1, col, valueToCheck)) {
                result.add(index);
            }
        }

        // rechtsboven waarde
        if (indexcheck(width, height, row-1, col+1)) {
            int index = bepaalindex(row-1,width,col+1);
            if (vergelijkwaarde(gridList,width, row-1, col+1, valueToCheck)) {
                result.add(index);
            }
        }

        // linker waarde
        if (indexcheck(width, height, row, col-1)) {
            int index = bepaalindex(row,width,col-1);
            if (vergelijkwaarde(gridList,width, row, col-1, valueToCheck)) {
                result.add(index);
            }
        }

        // rechter waarde
        if (indexcheck(width, height, row, col+1)) {
            int index = bepaalindex(row,width,col+1);
            if (vergelijkwaarde(gridList,width, row, col+1, valueToCheck)) {
                result.add(index);
            }
        }

        // linksonder waarde
        if (indexcheck(width, height, row+1, col-1)) {
            int index = bepaalindex(row+1,width,col-1);
            if (vergelijkwaarde(gridList,width, row+1, col-1, valueToCheck)) {
                result.add(index);
            }
        }

        // onderste waarde
        if (indexcheck(width, height, row+1, col)) {
            int index = bepaalindex(row+1,width,col);
            if (vergelijkwaarde(gridList,width, row+1, col, valueToCheck)) {
                result.add(index);
            }
        }

        // rechtsonder waarde
        if (indexcheck(width, height, row+1, col+1)) {
            int index = bepaalindex(row+1,width,col+1);
            if (vergelijkwaarde(gridList,width, row+1, col+1, valueToCheck)) {
                result.add(index);
            }
        }

        return result;
    }
    private static int bepaalindex(int row, int width,int col) {
        return row * width + col;
    }
    private static boolean indexcheck(int width, int height, int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
    private static boolean vergelijkwaarde(List<Integer> gridList, int width, int row, int col,int valueToCheck) {
        return (gridList.get(row * width + col) == valueToCheck);
    }
}