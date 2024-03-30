package be.kuleuven.candycrush;

import java.util.*;
import java.util.function.Function;

public class Board<T> {
    private final Map<Position, T> cells;
    private final Map<T, Set<Position>> reverseMap;
    public final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        this.cells = new HashMap<>();
        this.reverseMap = new HashMap<>();
    }

    public T getCellAt(Position position) {
        return cells.get(position);
    }

    public void replaceCellAt(Position position, T newCell) {
        T oldCell = cells.put(position, newCell);
        if (oldCell != null) {
            removeFromReverseMap(oldCell, position);
        }
        addToReverseMap(newCell, position);
    }


    public void fill(Function<Position, T> cellCreator) {
        for (int row = 0; row < boardSize.rows(); row++) {
            for (int column = 0; column < boardSize.columns(); column++) {
                Position position = new Position(row, column, boardSize);
                T cell = cellCreator.apply(position);
                cells.put(position, cell);
                addToReverseMap(cell, position); // Add to reverse map
            }
        }
    }

    public void copyTo(Board<T> otherBoard) {
        if (!this.boardSize.equals(otherBoard.boardSize)) {
            throw new IllegalArgumentException("Boards have different sizes");
        }

        otherBoard.cells.clear(); // Clear the other board before copying

        for (Map.Entry<Position, T> entry : this.cells.entrySet()) {
            Position position = entry.getKey();
            T cell = entry.getValue();
            otherBoard.cells.put(position, cell);
        }
    }

    private void addToReverseMap(T cell, Position position) {
        reverseMap.computeIfAbsent(cell, k -> new HashSet<>()).add(position);
    }

    private void removeFromReverseMap(T cell, Position position) {
        Set<Position> positions = reverseMap.get(cell);
        if (positions != null) {
            positions.remove(position);
            if (positions.isEmpty()) {
                reverseMap.remove(cell);
            }
        }
    }

    public Set<Position> getPositionsOfElement(T element) {
        return Set.copyOf(reverseMap.getOrDefault(element, Collections.emptySet()));
    }
}
