package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @Test
    public void testGetAndReplaceCellAt() {
        BoardSize boardSize = new BoardSize(3, 3);
        Board<Integer> board = new Board<>(boardSize);
        Position position = new Position(1, 1, boardSize);
        assertNull(board.getCellAt(position));
        board.replaceCellAt(position, 5);
        assertEquals(5, board.getCellAt(position));
    }

    @Test
    public void testFill() {
        BoardSize boardSize = new BoardSize(2, 2);
        Board<String> board = new Board<>(boardSize);
        board.fill(position -> "Cell at (" + position.row() + ", " + position.column() + ")");
        assertEquals("Cell at (0, 0)", board.getCellAt(new Position(0, 0, boardSize)));
        assertEquals("Cell at (0, 1)", board.getCellAt(new Position(0, 1, boardSize)));
        assertEquals("Cell at (1, 0)", board.getCellAt(new Position(1, 0, boardSize)));
        assertEquals("Cell at (1, 1)", board.getCellAt(new Position(1, 1, boardSize)));
    }

    @Test
    public void testCopyTo() {
        BoardSize boardSize = new BoardSize(2, 2);
        Board<Integer> board1 = new Board<>(boardSize);
        board1.fill(position -> position.row() * 10 + position.column());

        Board<Integer> board2 = new Board<>(boardSize);
        board1.copyTo(board2);

        for (int row = 0; row < boardSize.rows(); row++) {
            for (int column = 0; column < boardSize.columns(); column++) {
                assertEquals(board1.getCellAt(new Position(row, column, boardSize)),
                        board2.getCellAt(new Position(row, column, boardSize)));
            }
        }
    }
}
