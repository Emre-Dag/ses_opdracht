package be.kuleuven.candycrush;

public sealed interface Candy permits NormalCandy, ChocoCrunch, CaramelBlast, LemonDrop, BerryBurst {

}

record NormalCandy(int color) implements Candy {
    public NormalCandy {
        if (color < 0 || color > 3) {
            throw new IllegalArgumentException("Color must be 0, 1, 2, or 3.");
        }
    }
}

record ChocoCrunch() implements Candy {
    // Implementation for ChocoCrunch
}

record CaramelBlast() implements Candy {
    // Implementation for CaramelBlast
}

record LemonDrop() implements Candy {
    // Implementation for LemonDrop
}

record BerryBurst() implements Candy {
    // Implementation for BerryBurst
}
