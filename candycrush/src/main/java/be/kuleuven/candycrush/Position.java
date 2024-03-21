package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.List;

public record Position(int row, int column, BoardSize boardSize) {
    public Position {
        if (row < 0 || row >= boardSize.rows() || column < 0 || column >= boardSize.columns()) {
            throw new IllegalArgumentException("Ongeldige positie op het speelveld.");
        }
    }

    public int toIndex() {
        return row * boardSize.columns() + column;
    }
    public static Position fromIndex(int index, BoardSize size) {
        if (index < 0 || index >= size.rows() * size.columns()) {
            throw new IllegalArgumentException("Ongeldige index.");
        }
        return new Position(index / size.columns(),
                            index % size.columns(),
                                    size);
    }
    public Iterable<Position> neighborPositions() {
        List<Position> neighbors = new ArrayList<>();
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1}; // Veranderingen in rijen voor naburige posities
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1}; // Veranderingen in kolommen voor naburige posities

        for (int i = 0; i < dr.length; i++) {
            int newRow = row + dr[i];
            int newColumn = column + dc[i];
            if (row >= 0 && row < boardSize.rows() && column >= 0 && column < boardSize.columns()) {
                neighbors.add(new Position(newRow, newColumn, boardSize));
            }
        }

        return neighbors;
    }

    public boolean isLastColumn() {
        return column == boardSize.columns() - 1;
    }

}