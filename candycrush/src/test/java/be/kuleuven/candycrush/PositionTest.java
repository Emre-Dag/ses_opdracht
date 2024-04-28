package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.stream.Collectors;

public class PositionTest {

    @Test
    public void testToIndex() {
        BoardSize boardSize = new BoardSize(2, 4);

        Position position1 = new Position(0, 0, boardSize);
        assertEquals(0, position1.toIndex());

        Position position2 = new Position(0, 1, boardSize);
        assertEquals(1, position2.toIndex());

        Position position3 = new Position(1, 0, boardSize);
        assertEquals(4, position3.toIndex());

        Position position4 = new Position(1, 3, boardSize);
        assertEquals(7, position4.toIndex());
    }


    @Test
    public void testFromIndex_ValidIndex() {
        BoardSize boardSize = new BoardSize(2, 4);

        Position position1 = Position.fromIndex(0, boardSize);
        assertEquals(0, position1.row());
        assertEquals(0, position1.column());

        Position position2 = Position.fromIndex(1, boardSize);
        assertEquals(0, position2.row());
        assertEquals(1, position2.column());

        Position position3 = Position.fromIndex(4, boardSize);
        assertEquals(1, position3.row());
        assertEquals(0, position3.column());

        Position position4 = Position.fromIndex(7, boardSize);
        assertEquals(1, position4.row());
        assertEquals(3, position4.column());
    }

    @Test
    public void testFromIndex_InvalidIndex() {
        BoardSize boardSize = new BoardSize(2, 4);

        assertThrows(IllegalArgumentException.class, () -> {
            Position.fromIndex(-1, boardSize);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Position.fromIndex(8, boardSize);
        });
    }

    @Test
    public void testNeighborPositions() {
        BoardSize boardSize = new BoardSize(3, 3);
        Position center = new Position(1, 1, boardSize);

        Iterable<Position> neighbors = center.neighborPositions();
        assertNotNull(neighbors);

        // Expected neighbors for center position (1, 1)
        Position[] expectedNeighbors = {
                new Position(0, 1, boardSize), // North
                new Position(2, 1, boardSize), // South
                new Position(1, 0, boardSize), // West
                new Position(1, 2, boardSize), // East
                new Position(0, 0, boardSize), // Northwest
                new Position(0, 2, boardSize), // Northeast
                new Position(2, 0, boardSize), // Southwest
                new Position(2, 2, boardSize)  // Southeast
        };

        for (Position expectedNeighbor : expectedNeighbors) {
            boolean found = false;
            for (Position neighbor : neighbors) {
                if (neighbor.row() == expectedNeighbor.row() && neighbor.column() == expectedNeighbor.column()) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Expected neighbor not found: " + expectedNeighbor);
        }
    }
    @Test
    public void testIsLastColumn() {
        BoardSize boardSize = new BoardSize(3, 3);

        Position position1 = new Position(0, 0, boardSize);
        assertFalse(position1.isLastColumn());

        Position position2 = new Position(0, 2, boardSize);
        assertTrue(position2.isLastColumn());

        Position position3 = new Position(1, 1, boardSize);
        assertFalse(position3.isLastColumn());

        Position position4 = new Position(2, 2, boardSize);
        assertTrue(position4.isLastColumn());
    }
    private CandyCrushModel initializeModelWithCandies(BoardSize boardSize) {
        CandyCrushModel model = new CandyCrushModel(boardSize);
        // Manually populate the board with candies
        model.candyBoard.replaceCellAt(new Position(0, 0, boardSize), new NormalCandy(0));  // Row 0, Column 0
        model.candyBoard.replaceCellAt(new Position(0, 1, boardSize), new NormalCandy(1));  // Row 0, Column 1
        model.candyBoard.replaceCellAt(new Position(0, 2, boardSize), new NormalCandy(1));  // Row 0, Column 2
        model.candyBoard.replaceCellAt(new Position(0, 3, boardSize), new NormalCandy(1));  // Row 0, Column 3
        model.candyBoard.replaceCellAt(new Position(0, 4, boardSize), new NormalCandy(0));  // Row 0, Column 4

        model.candyBoard.replaceCellAt(new Position(1, 0, boardSize), new NormalCandy(1));  // Row 1, Column 0
        model.candyBoard.replaceCellAt(new Position(1, 1, boardSize), new NormalCandy(2));  // Row 1, Column 1
        model.candyBoard.replaceCellAt(new Position(1, 2, boardSize), new NormalCandy(1));  // Row 1, Column 2
        model.candyBoard.replaceCellAt(new Position(1, 3, boardSize), new NormalCandy(0));  // Row 1, Column 3
        model.candyBoard.replaceCellAt(new Position(1, 4, boardSize), new NormalCandy(1));  // Row 1, Column 4

        model.candyBoard.replaceCellAt(new Position(2, 0, boardSize), new NormalCandy(2));  // Row 2, Column 0
        model.candyBoard.replaceCellAt(new Position(2, 1, boardSize), new NormalCandy(2));  // Row 2, Column 1
        model.candyBoard.replaceCellAt(new Position(2, 2, boardSize), new NormalCandy(0));  // Row 2, Column 2
        model.candyBoard.replaceCellAt(new Position(2, 3, boardSize), new NormalCandy(1));  // Row 2, Column 3
        model.candyBoard.replaceCellAt(new Position(2, 4, boardSize), new NormalCandy(2));  // Row 2, Column 4

        model.candyBoard.replaceCellAt(new Position(3, 0, boardSize), new NormalCandy(3));  // Row 3, Column 0
        model.candyBoard.replaceCellAt(new Position(3, 1, boardSize), new NormalCandy(2));  // Row 3, Column 1
        model.candyBoard.replaceCellAt(new Position(3, 2, boardSize), new NormalCandy(1));  // Row 3, Column 2
        model.candyBoard.replaceCellAt(new Position(3, 3, boardSize), new NormalCandy(2));  // Row 3, Column 3
        model.candyBoard.replaceCellAt(new Position(3, 4, boardSize), new NormalCandy(3));  // Row 3, Column 4

        model.candyBoard.replaceCellAt(new Position(4, 0, boardSize), new NormalCandy(0));  // Row 4, Column 0
        model.candyBoard.replaceCellAt(new Position(4, 1, boardSize), new NormalCandy(1));  // Row 4, Column 1
        model.candyBoard.replaceCellAt(new Position(4, 2, boardSize), new NormalCandy(2));  // Row 4, Column 2
        model.candyBoard.replaceCellAt(new Position(4, 3, boardSize), new NormalCandy(3));  // Row 4, Column 3
        model.candyBoard.replaceCellAt(new Position(4, 4, boardSize), new NormalCandy(0));  // Row 4, Column 4
        //0 1 1 1 0
        //1 2 1 0 1
        //2 2 0 1 2
        //3 2 1 2 3
        //0 1 2 3 0
        return model;
    }
    @Test
    void walkTest() {
        // Initialize the board with candies
        BoardSize boardSize = new BoardSize(5, 5);
        Position position = new Position(2, 2, boardSize); // Example position
        System.out.println(position.walkLeft().collect(Collectors.toSet()));
        System.out.println(position.walkRight().collect(Collectors.toSet()));
        System.out.println(position.walkUp().collect(Collectors.toSet()));
        System.out.println(position.walkDown().collect(Collectors.toSet()));

    }
}