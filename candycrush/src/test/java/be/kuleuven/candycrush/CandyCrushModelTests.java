package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CandyCrushModelTests {

    @Test
    public void testGenerateRandomGrid_ReturnsNonEmpty() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4)); // Voorbeeld BoardSize
        Iterable<Candy> grid = model.generateRandomGrid();
        assertNotNull(grid);
        assertTrue(grid.iterator().hasNext()); // Check of de grid niet leeg is
    }

    // Test voor het updaten en ontvangen van een waarde op een specifieke positie in het grid
    @Test
    public void testUpdateAndGetGridValue_CorrectValue() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        Position position = new Position(1, 1,new BoardSize(4, 4)); // Voorbeeld positie
        Candy expectedCandy = new NormalCandy(1); // Voorbeeld candy om te plaatsen
        model.updateGridValue(position, expectedCandy);

        Candy actualCandy = model.getGridValue(position);
        assertEquals(expectedCandy, actualCandy);
    }

    @Test
    public void testSetAndGetPlayerName() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        String playerName = "TestPlayer";
        model.setPlayerName(playerName);
        assertEquals(playerName, model.getPlayerName());
    }

    @Test
    public void testUpdateScore_WithIncrement() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        int initialScore = model.getScore();
        int scoreIncrement = 5;
        model.updateScore(scoreIncrement);
        assertEquals(initialScore + scoreIncrement, model.getScore());
    }

    @Test
    public void testUpdateGrid_WithNull_ThrowsException() {
        CandyCrushModel model = new CandyCrushModel(new BoardSize(4, 4));
        assertThrows(NullPointerException.class, () -> model.updateGrid(null));
    }

}
