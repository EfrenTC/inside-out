package dev.efren.utils;

public enum Emocion {
    ALEGRIA,
    TRISTEZA,
    IRA,
    ASCO,
    MIEDO,
    ANSIEDAD,
    ENVIDIA,
    VERGUENZA,
    ABURRIMIENTO,
    NOSTALGIA;

    public static Emocion fromInt(int opcion) {
        return Emocion.values()[opcion - 1];
    }

    public static void mostrarEmociones() {
        for (int i = 0; i < Emocion.values().length; i++) {
            System.out.println((i + 1) + ". " + Emocion.values()[i]);
        }
    }
}
