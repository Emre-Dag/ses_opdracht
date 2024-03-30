package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @Test
    public void givenEmptyBoard_whenGettingCellAtValidPosition_thenCellShouldBeNull() {
        // Arrange
        Board<String> board = new Board<>(new BoardSize(3, 3));

        // Act
        String cell = board.getCellAt(new Position(1, 1,board.boardSize));

        // Assert
        assertNull(cell);
    }

    @Test
    public void givenBoardWithCell_whenReplacingCell_thenNewCellShouldBeRetrieved() {
        // Arrange
        Board<Integer> board = new Board<>(new BoardSize(3, 3));
        Position position = new Position(1, 1,board.boardSize);
        int newCell = 42;

        // Act
        board.replaceCellAt(position, newCell);
        Integer retrievedCell = board.getCellAt(position);

        // Assert
        assertEquals(newCell, retrievedCell);
    }

    @Test
    public void givenBoardFilledWithCells_whenGettingPositionsOfElement_thenAllPositionsShouldBeReturned() {
        // Arrange
        Board<String> board = new Board<>(new BoardSize(3, 3));
        String element = "test";
        Set<Position> expectedPositions = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Position position = new Position(i, j,board.boardSize);
                expectedPositions.add(position);
                board.replaceCellAt(position, element);
            }
        }

        // Act
        Set<Position> positions = board.getPositionsOfElement(element);

        // Assert
        assertEquals(expectedPositions, positions);
    }

    @Test
    public void givenBoardWithOneCell_whenGettingPositionsOfNonexistentElement_thenEmptySetShouldBeReturned() {
        // Arrange
        Board<String> board = new Board<>(new BoardSize(3, 3));
        Position position = new Position(1, 1,board.boardSize);
        String element = "test";
        board.replaceCellAt(position, element);

        // Act
        Set<Position> positions = board.getPositionsOfElement("nonexistent");

        // Assert
        assertTrue(positions.isEmpty());
    }
}
