package dev.efren.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MomentoTest {

    @Test
    void constructorAndGetters() {
        LocalDate fecha = LocalDate.of(2024, 1, 1);
        Momento m = new Momento(1, "Titulo", "Descripcion", Emocion.ALEGRIA, fecha, true);

        assertEquals(1, m.getId());
        assertEquals("Titulo", m.getTitulo());
        assertEquals("Descripcion", m.getDescripcion());
        assertEquals(Emocion.ALEGRIA, m.getEmocion());
        assertEquals(fecha, m.getFechaOcurrencia());
        assertTrue(m.isEsBueno());
        assertNotNull(m.getFechaCreacion());
        assertNotNull(m.getFechaModificacion());
    }

    @Test
    void settersUpdateFieldsAndTouch() throws InterruptedException {
        Momento m = new Momento(2, "Old", "OldDesc", Emocion.MIEDO, LocalDate.now(), false);

        LocalDateTime modInicial = m.getFechaModificacion();
        Thread.sleep(5); 

        m.setTitulo("NuevoTitulo");
        m.setDescripcion("NuevaDesc");
        m.setEmocion(Emocion.ALEGRIA);
        m.setFechaOcurrencia(LocalDate.of(2025, 5, 5));
        m.setEsBueno(true);

        assertEquals("NuevoTitulo", m.getTitulo());
        assertEquals("NuevaDesc", m.getDescripcion());
        assertEquals(Emocion.ALEGRIA, m.getEmocion());
        assertEquals(LocalDate.of(2025, 5, 5), m.getFechaOcurrencia());
        assertTrue(m.isEsBueno());
        assertTrue(m.getFechaModificacion().isAfter(modInicial));
    }

    @Test
    void toStringContainsAllFields() {
        Momento m = new Momento(3, "T", "D", Emocion.TRISTEZA, LocalDate.of(2024, 2, 2), false);
        String str = m.toString();

        assertTrue(str.contains("3."));
        assertTrue(str.contains("T"));
        assertTrue(str.contains("D"));
        assertTrue(str.contains("TRISTEZA"));
        assertTrue(str.contains("Malo")); 
    }

    @Test
    void equalsAndHashCodeBasedOnId() {
        Momento m1 = new Momento(10, "A", "B", Emocion.ALEGRIA, LocalDate.now(), true);
        Momento m2 = new Momento(10, "Otro", "OtroDesc", Emocion.TRISTEZA, LocalDate.now(), false);
        Momento m3 = new Momento(11, "A", "B", Emocion.ALEGRIA, LocalDate.now(), true);

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());

        assertNotEquals(m1, m3);
        assertNotEquals(m1.hashCode(), m3.hashCode());

        assertNotEquals(m1, null);
        assertNotEquals(m1, "string");
    }
}
