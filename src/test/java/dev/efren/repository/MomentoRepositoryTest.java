package dev.efren.repository;

import dev.efren.model.Emocion;
import dev.efren.model.Momento;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MomentoRepositoryTest {

    private MomentoRepository repo;
    private final String DB_FILE = "test_momentos.db";

    @BeforeEach
    void setup() {
        repo = new MomentoRepository(DB_FILE);
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
        assertFalse(repo.deleteById(999));
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

    @Test
    void findByIdBuenosMalos() {
        Momento bueno = repo.add("Bueno", "desc", Emocion.ALEGRIA, LocalDate.now(), true);
        Momento malo = repo.add("Malo", "desc", Emocion.TRISTEZA, LocalDate.now(), false);

        Optional<Momento> encontrado = repo.findById(bueno.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Bueno", encontrado.get().getTitulo());

        assertTrue(repo.findById(999).isEmpty());

        List<Momento> buenos = repo.findBuenos();
        assertEquals(1, buenos.size());
        assertEquals("Bueno", buenos.get(0).getTitulo());

        List<Momento> malos = repo.findMalos();
        assertEquals(1, malos.size());
        assertEquals("Malo", malos.get(0).getTitulo());
    }

    @Test
    void exportToCSVSuccess() throws IOException {
        repo.add("CSV Test", "Descripcion", Emocion.ALEGRIA, LocalDate.of(2024, 5, 20), true);

        String filePath = "test_export.csv";
        repo.exportToCSV(filePath);

        File f = new File(filePath);
        assertTrue(f.exists());
        String contenido = Files.readString(f.toPath());
        assertTrue(contenido.contains("CSV Test"));

        f.delete();
    }

    @Test
    void exportToCSVFailure() {
        repo.add("X", "Y", Emocion.MIEDO, LocalDate.now(), true);
        repo.exportToCSV("/ruta/inexistente/file.csv");
    }

    @Test
    void persistAndLoad() {
    
        repo.add("Persistido", "desc", Emocion.NOSTALGIA, LocalDate.of(2024,3,3), true);
        MomentoRepository repo2 = new MomentoRepository(DB_FILE);
        List<Momento> all = repo2.findAll();
        assertEquals(1, all.size());
        assertEquals("Persistido", all.get(0).getTitulo());

        repo2.clear();
    }
}
