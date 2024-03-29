package be.kuleuven.candycrush;

import java.util.function.Function;

public class Board<T> {
    public final T[][] cells;
    public final BoardSize boardSize;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        this.cells = (T[][]) new Object[boardSize.rows()][boardSize.columns()];
    }

    public T getCellAt(Position position) {
        return cells[position.row()][position.column()];
    }

    public void replaceCellAt(Position position, T newCell) {
        cells[position.row()][position.column()] = newCell;
    }

    public void fill(Function<Position, T> cellCreator) {
        for (int row = 0; row < boardSize.rows(); row++) {
            for (int column = 0; column < boardSize.columns(); column++) {
                Position position = new Position(row, column, boardSize);
                cells[row][column] = cellCreator.apply(position);
            }
        }
    }

    public void copyTo(Board<T> otherBoard) {
        if (!this.boardSize.equals(otherBoard.boardSize)) {
            throw new IllegalArgumentException("Boards have different sizes");
        }

        for (int row = 0; row < boardSize.rows(); row++) {
            for (int column = 0; column < boardSize.columns(); column++) {
                otherBoard.cells[row][column] = this.cells[row][column];
            }
        }
    }
}
