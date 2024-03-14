package be.kuleuven.candycrush;
import be.kuleuven.getSameNeighboursIds;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CandyCrushModelTests {

    @Test
    public void test_GenereerWillekeurigGrid_RetourneertNietLeeg() {
        CandyCrushModel model = new CandyCrushModel();
        Iterable<Integer> grid = model.generateRandomGrid(4, 4);
        assertNotNull(grid);
    }

    @Test
    public void test_OntvangWaardeVanGrid_GeeftCorrecteWaardeTerug() {
        CandyCrushModel model = new CandyCrushModel();
        List<Integer> gridList = Arrays.asList(
                0, 1, 2, 3,
                1, 2, 0, 3,
                3, 0, 2, 1,
                2, 1, 3, 0
        );
        int value = model.getGridValue(gridList, 4, 1, 2);
        assertEquals(0, value);
    }

    @Test
    public void test_UpdateGridWaarde_UpdateCorrect() {
        CandyCrushModel model = new CandyCrushModel();
        List<Integer> gridList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3));
        model.updateGrid(gridList);
        model.updateGridValue(5, 3);
        Iterable<Integer> updatedGrid = model.getGrid();

        int value = 0;
        for (int v : updatedGrid) {
            if (value == 5) {
                assertEquals(3, v);
                break;
            }
            value++;
        }
    }

    @Test
    public void test_StelSpelersnaamInEnOntvangSpelersnaam() {
        // Create a new CandyCrushModel instance
        CandyCrushModel model = new CandyCrushModel();

        // Set the player name
        String playerName = "Emre";
        model.setPlayerName(playerName);

        // Check if the set player name matches the retrieved player name
        assertEquals(playerName, model.getPlayerName());
    }

    @Test
    public void test_UpdateScore_GegevenIncrement_ZetScore() {
        // Create a new CandyCrushModel instance
        CandyCrushModel model = new CandyCrushModel();

        // Update the score
        int scoreIncrement = 10;
        model.updateScore(scoreIncrement);

        // Check if the score has been updated correctly
        assertEquals(scoreIncrement, model.getScore());
    }
    @Test
    public void test_GetBuren_MiddenVanGrid() {
        // Lijst met waarden die een 4x4 grid voorstellen
        List<Integer> gridList = Arrays.asList(
                0, 1, 2, 3,
                1, 2, 0, 3,
                3, 0, 2, 1,
                2, 1, 3, 0
        );

        // Breedte en hoogte van het grid
        int width = 4;
        int height = 4;

        // Index van een cel in het midden van het grid
        int indexToCheck = 5; // Dit is de index voor de cel (1, 1) in een 4x4 grid

        // Verwachte buren voor de cel (1, 1)
        List<Integer> expectedNeighbours = Arrays.asList(2,10);

        // Haal de buren op met behulp van de getNeighbours-methode
        Iterable<Integer> neighbours = getSameNeighboursIds.getSameNeighboursIds(gridList, width, height, indexToCheck);

        // Converteer de Iterable naar een lijst voor eenvoudige vergelijking
        List<Integer> actualNeighbours = new ArrayList<>();
        neighbours.forEach(actualNeighbours::add);

        // Controleer of de verwachte buren overeenkomen met de daadwerkelijke buren
        assertEquals(expectedNeighbours.size(), actualNeighbours.size());
        assertTrue(actualNeighbours.containsAll(expectedNeighbours));
    }
    @Test
    public void test_GetGridValue_WerpExceptionBijOngeldigeIndices() {
        CandyCrushModel model = new CandyCrushModel();
        List<Integer> gridList = Arrays.asList(
                0, 1, 2, 3,
                1, 2, 0, 3,
                3, 0, 2, 1,
                2, 1, 3, 0
        );

        // Ongeldige indices buiten het bereik van het grid
        int invalidRow = 5;
        int invalidCol = 2;

        // Controleer of een IndexOutOfBoundsException wordt gegooid
        assertThrows(IndexOutOfBoundsException.class, () -> model.getGridValue(gridList, 4, invalidRow, invalidCol));
    }

    @Test
    public void test_UpdateScore_MetNegatieveWaarde() {
        CandyCrushModel model = new CandyCrushModel();
        int initialScore = model.getScore();
        int scoreDecrement = -10;
        model.updateScore(scoreDecrement);
        int updatedScore = model.getScore();

        // Controleer of de score correct is bijgewerkt door de negatieve waarde
        assertEquals(initialScore + scoreDecrement, updatedScore);
    }

    @Test
    public void test_UpdateGrid_MetNullNieuweGridlijst_GooitException() {
        CandyCrushModel model = new CandyCrushModel();
        List<Integer> gridList = null;

        // Controleer of een NullPointerException wordt gegooid wanneer een null nieuwe gridlijst wordt doorgegeven aan updateGrid
        assertThrows(NullPointerException.class, () -> model.updateGrid(gridList));
    }

    @Test
    public void test_StelSpelersnaamIn_MetLegeNaam_GooitException() {
        CandyCrushModel model = new CandyCrushModel();

        // Test met een lege spelersnaam
        String legeNaam = "";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            model.setPlayerName(legeNaam);
        });

        // Verifieer dat de juiste uitzondering wordt opgegooid
        assertEquals("Player name cannot be empty", exception.getMessage());
    }
}
