package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CandyCrushModelTests {

    @Test
    public void givenCandyCrushModel_whenInitialized_thenPlayerNameIsPlayer1() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        assertEquals("Player1", model.getPlayerName());
    }

    @Test
    public void givenCandyCrushModel_whenSetPlayerName_thenPlayerNameIsUpdated() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        model.setPlayerName("TestPlayer");
        assertEquals("TestPlayer", model.getPlayerName());
    }

    @Test
    public void givenCandyCrushModel_whenInitialized_thenScoreIsZero() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        assertEquals(0, model.getScore());
    }

    @Test
    public void givenCandyCrushModel_whenUpdateScore_thenScoreIsUpdated() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        model.updateScore(10);
        assertEquals(10, model.getScore());
    }

    @Test
    public void givenCandyCrushModel_whenGetCandyAt_thenCandyIsNotNull() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        Position position = new Position(0, 0, model.boardSize);
        assertNotNull(model.getCandyAt(position));
    }

    @Test
    public void givenCandyCrushModel_whenUpdateCandyAt_thenCandyIsUpdated() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        Position position = new Position(0, 0, model.boardSize);
        Candy candy = new NormalCandy(0);
        model.updateCandyAt(position, candy);
        assertEquals(candy, model.getCandyAt(position));
    }

    @Test
    public void givenCandyCrushModel_whenGetSameNeighbourPositions_thenPositionsAreNotNull() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        Position position = new Position(0, 0, model.boardSize);
        assertNotNull(model.getSameNeighbourPositions(position));
    }

    @Test
    public void givenCandyCrushModel_whenCreateRandomCandy_thenCandyIsNotNull() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        assertNotNull(model.createRandomCandy(new Position(0, 0, model.boardSize)));
    }
    @Test
    public void givenCandyCrushModel_whenGenerateRandomGrid_thenBoardIsFilledWithCandies() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        model.generateRandomGrid();
        Board<Candy> candyBoard = model.getCandyBoard();
        for (int row = 0; row < candyBoard.boardSize.rows(); row++) {
            for (int col = 0; col < candyBoard.boardSize.columns(); col++) {
                assertNotNull(candyBoard.getCellAt(new Position(row, col,candyBoard.boardSize)));
            }
        }
    }
    @Test
    public void givenCandyCrushModel_whenCompareSameCandy_thenReturnsTrue() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        Candy candy1 = new NormalCandy(0);
        Candy candy2 = new NormalCandy(0);
        assertTrue(model.compareCandyAtPosition(candy1, candy2));
    }

}