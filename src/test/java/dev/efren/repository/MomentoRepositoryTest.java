package dev.efren.repository;

import dev.efren.model.Emocion;
import dev.efren.model.Momento;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MomentoRepositoryTest {

    private MomentoRepository repo;

    @BeforeEach
    void setup() {
        repo = new MomentoRepository("test_momentos.db");
        repo.clear();
    }

    @AfterEach
    void tearDown() {
        repo.clear();
    }

    @Test
    void addAndFindAll() {
        Momento m = repo.add("T", "D", Emocion.ALEGRIA, LocalDate.of(2024,1,1), true);
        List<Momento> all = repo.findAll();
        assertEquals(1, all.size());
        assertEquals(m.getId(), all.get(0).getId());
    }

    @Test
    void deleteById() {
        Momento m = repo.add("T", "D", Emocion.ALEGRIA, LocalDate.of(2024,1,1), true);
        assertTrue(repo.deleteById(m.getId()));
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void findByEmocionAndByYearMonth() {
        repo.add("A", "desc", Emocion.ALEGRIA, LocalDate.of(2024,1,1), true);
        repo.add("B", "desc", Emocion.TRISTEZA, LocalDate.of(2024,1,15), true);
        repo.add("C", "desc", Emocion.ALEGRIA, LocalDate.of(2024,2,1), true);

        List<Momento> alegr = repo.findByEmocion(Emocion.ALEGRIA);
        assertEquals(2, alegr.size());

        List<Momento> jan = repo.findByYearMonth(YearMonth.of(2024,1));
        assertEquals(2, jan.size());
    }
}
