package dev.efren.controller;

import dev.efren.model.Emocion;
import dev.efren.model.Momento;
import dev.efren.repository.MomentoRepository;
import org.junit.jupiter.api.*;

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
        Momento m = controller.addMomento("T", "D", Emocion.MIEDO, LocalDate.of(2023, 5, 2));
        Optional<Momento> found = controller.getById(m.getId());
        assertTrue(found.isPresent());
        assertEquals("T", found.get().getTitulo());
    }

    @Test
    void getByEmocionAndMonth() {
        controller.addMomento("A", "D", Emocion.NOSTALGIA, LocalDate.of(2024, 3, 1));
        controller.addMomento("B", "D", Emocion.NOSTALGIA, LocalDate.of(2024, 3, 5));
        controller.addMomento("C", "D", Emocion.ALEGRIA, LocalDate.of(2024, 4, 1));

        List<Momento> listEm = controller.getByEmocion(Emocion.NOSTALGIA);
        assertEquals(2, listEm.size());

        List<Momento> listMonth = controller.getByYearMonth(YearMonth.of(2024, 3));
        assertEquals(2, listMonth.size());
    }

    @Test
    void deleteWorks() {
        Momento m = controller.addMomento("T", "D", Emocion.IRA, LocalDate.now());
        boolean ok = controller.delete(m.getId());
        assertTrue(ok);
        assertTrue(controller.getAll().isEmpty());
    }
}
