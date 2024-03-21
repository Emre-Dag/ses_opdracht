package be.kuleuven.candycrush;
import java.util.ArrayList;
import java.util.List;
public record BoardSize(int rows, int columns) {
    public BoardSize {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Aantal rijen en aantal kolommen moeten groter zijn dan 0.");
        }
    }
    public Iterable<Position> positions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                positions.add(new Position(row, column, this));
            }
        }
        return positions;
    }
}