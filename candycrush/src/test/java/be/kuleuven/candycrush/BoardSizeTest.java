package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BoardSizeTest {

    @Test
    public void GeeftAllePosities_TerugInOplopendeIndexVolgorde() {
        BoardSize boardSize = new BoardSize(2, 3);
        Iterable<Position> positions = boardSize.positions();

        assertNotNull(positions);

        // Verwachte aantal posities voor een bord van 2x3
        int expectedSize = 2 * 3;
        int actualSize = 0;
        for (Position position : positions) {
            actualSize++;
        }
        assertEquals(expectedSize, actualSize);

        // Controleer of de posities oplopende indexen hebben
        int currentIndex = 0;
        for (Position position : positions) {
            int index = position.toIndex();
            assertEquals(currentIndex++, index);
        }
    }
}
