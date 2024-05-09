package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
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
    public void testFirstTwoHaveCandy() {
        BoardSize boardSize = new BoardSize(5, 5); // Assuming a 5x5 board for simplicity.
        CandyCrushModel model = initializeModelWithCandies(boardSize);
        assertFalse(model.firstTwoHaveCandy(new NormalCandy(0), Stream.of(
                new Position(0, 1, boardSize),
                new Position(0, 2, boardSize)
        )));
        assertTrue(model.firstTwoHaveCandy(new NormalCandy(1), Stream.of(
                new Position(0, 1, boardSize),
                new Position(0, 2, boardSize)
        )));
        assertTrue(model.firstTwoHaveCandy(new NormalCandy(2), Stream.of(
                new Position(2, 0, boardSize),
                new Position(2, 1, boardSize)
        )));
        assertFalse(model.firstTwoHaveCandy(new NormalCandy(0), Stream.of(
                new Position(2, 2, boardSize),
                new Position(2, 3, boardSize)
        )));
    }
    @Test
    public void testStartingPositions() {
        // Initialize the board with candies
        BoardSize boardSize = new BoardSize(5, 5);
        CandyCrushModel model = initializeModelWithCandies(boardSize);

        // Define expected results based on the predefined board setup
        Set<Position> expectedHorizontal = new HashSet<>();
        // Add positions where a horizontal swap results in a match
        expectedHorizontal.add(new Position(0, 1, boardSize)); // Potential match by swapping (0, 1) with (0, 2)

        Set<Position> expectedVertical = new HashSet<>();
        // Add positions where a vertical swap results in a match
        expectedVertical.add(new Position(0, 2, boardSize)); // Potential match by swapping (1, 2) with (2, 2)

        // Call the methods under test and convert the results from Stream to Set
        Set<Position> actualHorizontal = model.horizontalStartingPositions().collect(Collectors.toSet());
        Set<Position> actualVertical = model.verticalStartingPositions().collect(Collectors.toSet());

        // Assert that the identified starting positions match the expected results
        //assertEquals(expectedHorizontal, actualHorizontal, "Horizontal starting positions should match expected");
        //assertEquals(expectedVertical, actualVertical, "Vertical starting positions should match expected");

        // Print the results for manual verification if needed
        //System.out.println("Expected Horizontal Starting Positions: " + expectedHorizontal);
        System.out.println("Actual Horizontal Starting Positions: " + actualHorizontal);
        //System.out.println("Expected Vertical Starting Positions: " + expectedVertical);
        System.out.println("Actual Vertical Starting Positions: " + actualVertical);
    }

    @Test
    public void testFindAllMatches() {
        BoardSize boardSize = new BoardSize(5, 5); // Assuming a 5x5 board for simplicity.
        CandyCrushModel model = initializeModelWithCandies(boardSize);

        // Add more candies to create matches
        model.candyBoard.replaceCellAt(new Position(2, 2, boardSize), new NormalCandy(1)); // Create a vertical match

        //0 1 1 1 0
        //1 2 1 0 1
        //2 2 1 1 2
        //3 2 1 2 3
        //0 1 2 3 0

        // Expected matches
        Set<List<Position>> expectedMatches = new HashSet<>();
        expectedMatches.add(Arrays.asList(
                new Position(0, 1, boardSize),
                new Position(0, 2, boardSize),
                new Position(0, 3, boardSize)
        )); // Horizontal match
        expectedMatches.add(Arrays.asList(
                new Position(0, 2, boardSize),
                new Position(1, 2, boardSize),
                new Position(2, 2, boardSize),
                new Position(3, 2, boardSize)
        ));
        expectedMatches.add(Arrays.asList(
                new Position(1, 1, boardSize),
                new Position(2, 1, boardSize),
                new Position(3, 1, boardSize)
        ));
        // Vertical match

        Set<List<Position>> actualMatches = model.findAllMatches();
        System.out.println();
        // Print expected matches
        System.out.println("Expected Matches:");
        for (List<Position> match : expectedMatches) {
            System.out.println(match);
        }

        // Print actual matches
        System.out.println("\nActual Matches:");
        for (List<Position> match : actualMatches) {
            System.out.println(match);
        }

        assertEquals(expectedMatches, actualMatches);
    }
    @Test
    public void testClearMatch() {
        BoardSize boardSize = new BoardSize(5, 5); // Assuming a 5x5 board for simplicity.
        CandyCrushModel model = initializeModelWithCandies(boardSize);

        // Voeg een extra snoepje toe om een match te creëren
        model.candyBoard.replaceCellAt(new Position(2, 2, boardSize), new NormalCandy(1)); // Creëer een verticale match

        // Verwachte snoepjes die moeten worden verwijderd
        Set<Position> expectedRemovedCandies = new HashSet<>();
        expectedRemovedCandies.add(new Position(0, 2, boardSize));
        expectedRemovedCandies.add(new Position(1, 2, boardSize));
        expectedRemovedCandies.add(new Position(2, 2, boardSize));
        expectedRemovedCandies.add(new Position(3, 2, boardSize));
        model.printBoard();
        // Voer clearMatch uit voor de match die is gevonden
        List<Position> matchToRemove = Arrays.asList(
                new Position(0, 2, boardSize),
                new Position(1, 2, boardSize),
                new Position(2, 2, boardSize),
                new Position(3, 2, boardSize)
        );
        model.clearMatch(matchToRemove);
        System.out.println();
        model.printBoard();
        // Controleer of de verwijderde snoepjes inderdaad niet meer aanwezig zijn op het speelbord
        for (Position position : expectedRemovedCandies) {
            assertNull(model.getCandyAt(position));
        }
    }

    @Test
    public void testFallDownTo() {
        // Maak een bord van 5x5 en initialiseer het met snoepjes
        BoardSize boardSize = new BoardSize(5, 5);
        CandyCrushModel model = initializeModelWithCandies(boardSize);
        // Add null
        model.candyBoard.replaceCellAt(new Position(3, 3, boardSize), null); // Create a null
        model.candyBoard.replaceCellAt(new Position(1, 3, boardSize), null); // Create a null
        model.candyBoard.replaceCellAt(new Position(4, 3, boardSize), null); // Create a null
        //0 1 1 1 0
        //1 2 1 n 1
        //2 2 0 1 2
        //3 2 1 n 3
        //0 1 2 n 0
        model.printBoard();
        System.out.println();
        // Voer fallDownTo uit op positie (1,0)
        model.fallDownTo(new Position(4, 3, boardSize));
        System.out.println();
        model.printBoard();
        // Controleer of de snoepjes correct zijn gevallen
        assertEquals(new NormalCandy(1), model.candyBoard.getCellAt(new Position(4, 3, boardSize)));
        assertEquals(new NormalCandy(1), model.candyBoard.getCellAt(new Position(3, 3, boardSize)));
        assertNull(model.candyBoard.getCellAt(new Position(0, 3, boardSize)));
        assertNull(model.candyBoard.getCellAt(new Position(0, 3, boardSize)));
        assertNull(model.candyBoard.getCellAt(new Position(0, 3, boardSize)));
        assertEquals(new NormalCandy(1), model.candyBoard.getCellAt(new Position(3, 2, boardSize)));
    }

    @Test
    public void testUpdateBoard() {
        // Maak een bord van 5x5 en initialiseer het met snoepjes
        BoardSize boardSize = new BoardSize(5, 5);
        CandyCrushModel model = initializeModelWithCandies(boardSize);
        // Add more candies to create matches
        model.candyBoard.replaceCellAt(new Position(2, 2, boardSize), new NormalCandy(1)); // Create a vertical match

        //0 1 1 1 0
        //1 2 1 0 1
        //2 2 1 1 2
        //3 2 1 2 3
        //0 1 2 3 0
        // Voer de updateBoard-methode uit
        model.printBoard();
        boolean matchRemoved = model.updateBoard();
        System.out.println("Test result:");
        model.printBoard();
        System.out.println(model.score);
        // Controleer of minstens één match verwijderd is
        assertTrue(matchRemoved);
        System.out.println();
        // Controleer of er geen matches meer zijn
        assertFalse(model.updateBoard());
        System.out.println("Test 2:");
        CandyCrushModel model2 = initializeModelWith0and1(boardSize);
        // Voer de updateBoard-methode uit
        model2.printBoard();
        matchRemoved = model2.updateBoard();
        System.out.println("Test result 2:");
        model2.printBoard();
        // Controleer of minstens één match verwijderd is
        assertTrue(matchRemoved);
        System.out.println();
        // Controleer of er geen matches meer zijn
        assertFalse(model2.updateBoard());
        System.out.println(model2.score);

    }

    private CandyCrushModel initializeModelWith0and1(BoardSize boardSize) {
        CandyCrushModel model = new CandyCrushModel(boardSize);
        // Manually populate the board with candies
        model.candyBoard.replaceCellAt(new Position(0, 0, boardSize), new NormalCandy(1));  // Row 0, Column 0
        model.candyBoard.replaceCellAt(new Position(0, 1, boardSize), new NormalCandy(0));  // Row 0, Column 1
        model.candyBoard.replaceCellAt(new Position(0, 2, boardSize), new NormalCandy(0));  // Row 0, Column 2
        model.candyBoard.replaceCellAt(new Position(0, 3, boardSize), new NormalCandy(0));  // Row 0, Column 3
        model.candyBoard.replaceCellAt(new Position(0, 4, boardSize), new NormalCandy(1));  // Row 0, Column 4

        model.candyBoard.replaceCellAt(new Position(1, 0, boardSize), new NormalCandy(0));  // Row 1, Column 0
        model.candyBoard.replaceCellAt(new Position(1, 1, boardSize), new NormalCandy(1));  // Row 1, Column 1
        model.candyBoard.replaceCellAt(new Position(1, 2, boardSize), new NormalCandy(0));  // Row 1, Column 2
        model.candyBoard.replaceCellAt(new Position(1, 3, boardSize), new NormalCandy(1));  // Row 1, Column 3
        model.candyBoard.replaceCellAt(new Position(1, 4, boardSize), new NormalCandy(0));  // Row 1, Column 4

        model.candyBoard.replaceCellAt(new Position(2, 0, boardSize), new NormalCandy(0));  // Row 2, Column 0
        model.candyBoard.replaceCellAt(new Position(2, 1, boardSize), new NormalCandy(0));  // Row 2, Column 1
        model.candyBoard.replaceCellAt(new Position(2, 2, boardSize), new NormalCandy(1));  // Row 2, Column 2
        model.candyBoard.replaceCellAt(new Position(2, 3, boardSize), new NormalCandy(0));  // Row 2, Column 3
        model.candyBoard.replaceCellAt(new Position(2, 4, boardSize), new NormalCandy(0));  // Row 2, Column 4

        model.candyBoard.replaceCellAt(new Position(3, 0, boardSize), new NormalCandy(0));  // Row 3, Column 0
        model.candyBoard.replaceCellAt(new Position(3, 1, boardSize), new NormalCandy(1));  // Row 3, Column 1
        model.candyBoard.replaceCellAt(new Position(3, 2, boardSize), new NormalCandy(0));  // Row 3, Column 2
        model.candyBoard.replaceCellAt(new Position(3, 3, boardSize), new NormalCandy(1));  // Row 3, Column 3
        model.candyBoard.replaceCellAt(new Position(3, 4, boardSize), new NormalCandy(0));  // Row 3, Column 4

        model.candyBoard.replaceCellAt(new Position(4, 0, boardSize), new NormalCandy(1));  // Row 4, Column 0
        model.candyBoard.replaceCellAt(new Position(4, 1, boardSize), new NormalCandy(0));  // Row 4, Column 1
        model.candyBoard.replaceCellAt(new Position(4, 2, boardSize), new NormalCandy(0));  // Row 4, Column 2
        model.candyBoard.replaceCellAt(new Position(4, 3, boardSize), new NormalCandy(0));  // Row 4, Column 3
        model.candyBoard.replaceCellAt(new Position(4, 4, boardSize), new NormalCandy(1));  // Row 4, Column 4
        //0 1 0 1 0
        //1 0 1 0 1
        //0 0 1 0 0
        //1 0 1 0 1
        //0 1 0 1 0
        return model;
    }
    @Test
    public void testSwapCandies() {
        // Create a CandyCrushModel instance with a known initial state
        CandyCrushModel model = createBoardFromString("""
           @@o#
           o*#o
           @@**
           *#@@""");
        model.printBoard();
        System.out.println();
        // Perform the swap operation
        model.swapCandies(1, 0, 1, 1);

        model.printBoard();
    }

    @Test
    public void testNoValidSwitchesWithValidSwitch() {
        // Create a CandyCrushModel instance with a known initial state
        CandyCrushModel model = createBoardFromString("""
           @@o#
           o*#o
           @@**
           *#@@""");

        assertFalse(model.noValidSwitches());
    }
    @Test
    public void testMaximizeScore() {
        // Create a CandyCrushModel instance
        CandyCrushModel model =  createBoardFromString("""
           @@o#
           o*#o
           @@**
           *#@@""");
        model.printBoard();
        // Call the maximizeScore method
        var maximized = model.maximizeScore();
        System.out.println(maximized);
        // Output the best sequence of moves
        System.out.println("Best sequence of moves to maximize score:");
        for (List<Position> positions : model.bestSequence) {
            for (Position pos : positions) {
                System.out.print("(" + pos.row() + ", " + pos.column() + ") ");
            }
            //System.out.println();
        }
        System.out.println("max score: "+model.maxScore);
    }

    @Test
    public void testMaximizeScore2() {
        // Create a CandyCrushModel instance
        CandyCrushModel model = createBoardFromString("""
   #oo##
   #@o@@
   *##o@
   @@*@o
   **#*o""");
        model.printBoard();
        // Call the maximizeScore method
        var maximized = model.maximizeScore();
        System.out.println(maximized);
        // Output the best sequence of moves
        System.out.println("Best sequence of moves to maximize score:");
        for (List<Position> positions : model.bestSequence) {
            for (Position pos : positions) {
                System.out.print("(" + pos.row() + ", " + pos.column() + ") ");
            }
            //System.out.println();
        }
        System.out.println("max score: "+model.maxScore);
    }

    @Test
    public void testMaximizeScore3() {
        // Create a CandyCrushModel instance
        CandyCrushModel model = createBoardFromString("""
   #@#oo@
   @**@**
   o##@#o
   @#oo#@
   @*@**@
   *#@##*""");
        model.printBoard();
        // Call the maximizeScore method
        var maximized = model.maximizeScore();
        System.out.println(maximized);
        // Output the best sequence of moves
        System.out.println("Best sequence of moves to maximize score:");
        for (List<Position> positions : model.bestSequence) {
            for (Position pos : positions) {
                System.out.print("(" + pos.row() + ", " + pos.column() + ") ");
            }
            //System.out.println();
        }
        System.out.println("max score: "+model.maxScore);
    }
    public static CandyCrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        var model = createNewModel(size);
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                model.updateCandyAt(new Position(row, col, size), characterToCandy(line.charAt(col)));
            }
        }
        return model;
    }
    private static Candy characterToCandy(char c) {
        return switch(c) {
            case '.' -> null;
            case 'o' -> new NormalCandy(0);
            case '*' -> new NormalCandy(1);
            case '#' -> new NormalCandy(2);
            case '@' -> new NormalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
    public static CandyCrushModel createNewModel(BoardSize size) {
        // Creeer een nieuw model met de opgegeven grootte en een leeg speelbord
        return new CandyCrushModel(size);
    }
}