package dev.efren.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmocionTest {

    @Test
    void testToString() {
        assertEquals("Alegría", Emocion.ALEGRIA.toString());
        assertEquals("Tristeza", Emocion.TRISTEZA.toString());
        assertEquals("Vergüenza", Emocion.VERGÜENZA.toString());
    }

    @Test
    void testFromOrdinalValid() {
        assertEquals(Emocion.ALEGRIA, Emocion.fromOrdinal(1));
        assertEquals(Emocion.TRISTEZA, Emocion.fromOrdinal(2));
        assertEquals(Emocion.NOSTALGIA, Emocion.fromOrdinal(10));
    }

    @Test
    void testFromOrdinalInvalid() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> Emocion.fromOrdinal(0));
        assertTrue(ex1.getMessage().contains("inválida"));

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> Emocion.fromOrdinal(11));
        assertTrue(ex2.getMessage().contains("inválida"));
    }
}
