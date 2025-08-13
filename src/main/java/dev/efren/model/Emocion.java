package dev.efren.model;

public enum Emocion {
    ALEGRIA("Alegría"),
    TRISTEZA("Tristeza"),
    IRA("Ira"),
    ASCO("Asco"),
    MIEDO("Miedo"),
    ANSIEDAD("Ansiedad"),
    ENVIDIA("Envidia"),
    VERGÜENZA("Vergüenza"),
    ABURRIMIENTO("Aburrimiento"),
    NOSTALGIA("Nostalgia");

    private final String label;

    Emocion(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Emocion fromOrdinal(int ordinal) {
        Emocion[] values = values();
        if (ordinal < 1 || ordinal > values.length) {
            throw new IllegalArgumentException("Opción de emoción inválida: " + ordinal);
        }
        return values[ordinal - 1];
    }
}
