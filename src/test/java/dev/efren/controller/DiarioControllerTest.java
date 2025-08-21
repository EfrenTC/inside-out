package dev.efren.controller;

import dev.efren.model.Emocion;
import dev.efren.model.Momento;
import dev.efren.repository.MomentoRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DiarioControllerTest {

    private MomentoRepository repo;
    private DiarioController controller;

    @BeforeEach
    void setUp() {
        repo = new MomentoRepository("test2_momentos.db");
        repo.clear();
        controller = new DiarioController(repo);
    }

    @AfterEach
    void tearDown() {
        repo.clear();
    }

    @Test
    void addAndGetById() {
        Momento m = controller.addMomento("T", "D", Emocion.MIEDO, LocalDate.of(2023, 5, 2), false);
        Optional<Momento> found = controller.getById(m.getId());
        assertTrue(found.isPresent());
        assertEquals("T", found.get().getTitulo());
    }

    @Test
    void getByIdReturnsEmptyWhenNotFound() {
        Optional<Momento> result = controller.getById(999);
        assertTrue(result.isEmpty());
    }

    @Test
    void getByEmocionAndMonth() {
        controller.addMomento("A", "D", Emocion.NOSTALGIA, LocalDate.of(2024, 3, 1), false);
        controller.addMomento("B", "D", Emocion.NOSTALGIA, LocalDate.of(2024, 3, 5), false);
        controller.addMomento("C", "D", Emocion.ALEGRIA, LocalDate.of(2024, 4, 1), false);

        List<Momento> listEm = controller.getByEmocion(Emocion.NOSTALGIA);
        assertEquals(2, listEm.size());

        List<Momento> listMonth = controller.getByYearMonth(YearMonth.of(2024, 3));
        assertEquals(2, listMonth.size());
    }

    @Test
    void deleteWorks() {
        Momento m = controller.addMomento("T", "D", Emocion.IRA, LocalDate.now(), false);
        boolean ok = controller.delete(m.getId());
        assertTrue(ok);
        assertTrue(controller.getAll().isEmpty());
    }

    @Test
    void getAllReturnsAllMomentos() {
        controller.addMomento("Uno", "D", Emocion.ALEGRIA, LocalDate.now(), true);
        controller.addMomento("Dos", "D", Emocion.TRISTEZA, LocalDate.now(), false);

        List<Momento> all = controller.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void getBuenosAndMalosSeparateCorrectly() {
        controller.addMomento("Bueno", "D", Emocion.ALEGRIA, LocalDate.now(), true);
        controller.addMomento("Malo", "D", Emocion.IRA, LocalDate.now(), false);

        List<Momento> buenos = controller.getBuenos();
        List<Momento> malos = controller.getMalos();

        assertEquals(1, buenos.size());
        assertEquals("Bueno", buenos.get(0).getTitulo());

        assertEquals(1, malos.size());
        assertEquals("Malo", malos.get(0).getTitulo());
    }

    @Test
    void exportToCSVWorks() {
        controller.addMomento("CSV", "Probando", Emocion.NOSTALGIA, LocalDate.of(2024, 1, 1), true);

        String path = "test_export.csv";
        boolean ok = controller.exportToCSV(path);

        assertTrue(ok);

        File f = new File(path);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);

        f.delete();
    }

    @Test
    void exportToCSVHandlesError() {
        
        boolean ok = controller.exportToCSV("/ruta/que/no/existe/test.csv");
        assertFalse(ok);
    }
}
