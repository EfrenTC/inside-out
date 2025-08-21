package dev.efren.view;

import dev.efren.controller.DiarioController;
import dev.efren.model.Emocion;
import dev.efren.repository.MomentoRepository;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleViewTest {

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    private MomentoRepository repo;
    private DiarioController controller;

    @BeforeEach
    void setUp() {
        repo = new MomentoRepository("test_console.db");
        repo.clear();
        controller = new DiarioController(repo);

        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        repo.clear();
        System.setIn(System.in);
        System.setOut(System.out);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    void testAddAndViewAndExit() {
        String input = String.join(System.lineSeparator(),
                "1",
                "Un día especial",
                "01/05/2024",
                "Descripción...",
                "1",
                "s",     
                "2",
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Momento vivído añadido correctamente."));
        assertTrue(output.contains("Un día especial"));
        assertTrue(output.contains("Hasta la próxima!!!"));
    }

    @Test
    void testDeleteInvalidAndValid() {
        controller.addMomento("T", "D", Emocion.MIEDO, LocalDate.of(2024, 1, 1), false);

        String input = String.join(System.lineSeparator(),
                "3",
                "abc",   
                "3",
                "1",    
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Id inválido."));
        assertTrue(output.contains("Momento vivído eliminado correctamente."));
    }

    @Test
    void testFilterByEmocionOnly() {
        controller.addMomento("A", "desc", Emocion.ALEGRIA, LocalDate.of(2024, 1, 1), false);
        controller.addMomento("B", "desc", Emocion.TRISTEZA, LocalDate.of(2024, 1, 5), false);

        String input = String.join(System.lineSeparator(),
                "4",   
                "1",    
                "1",    
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("A"));
    }

    @Test
    void testFilterByFecha() {
        controller.addMomento("FechaTest", "desc", Emocion.NOSTALGIA, LocalDate.of(2024, 3, 15), true);

        String input = String.join(System.lineSeparator(),
                "4",
                "2",           
                "15/03/2024",  
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("FechaTest"));
    }

    @Test
    void testFilterBuenosAndMalos() {
        controller.addMomento("Bueno", "desc", Emocion.ALEGRIA, LocalDate.now(), true);
        controller.addMomento("Malo", "desc", Emocion.TRISTEZA, LocalDate.now(), false);

        String input = String.join(System.lineSeparator(),
                "4",
                "3",   
                "4",   
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Bueno"));
        assertTrue(output.contains("Malo"));
    }

    @Test
    void testExportCSVSuccess() {
        controller.addMomento("CSV", "desc", Emocion.IRA, LocalDate.of(2024, 1, 1), false);

        String input = String.join(System.lineSeparator(),
                "5",
                "test_console_export.csv",
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Momentos exportados correctamente"));

        File f = new File("test_console_export.csv");
        assertTrue(f.exists());
        f.delete();
    }

    @Test
    void testExportCSVFailure() {
        String input = String.join(System.lineSeparator(),
                "5",
                "/ruta/invalida/test.csv",
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Error al exportar momentos."));
    }

    @Test
    void testInvalidMenuOption() {
        String input = String.join(System.lineSeparator(),
                "9",  
                "7"  
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Opción no válida."));
        assertTrue(output.contains("Hasta la próxima!!!"));
    }

    @Test
    void testInvalidDateAndEmotionRetry() {
        String input = String.join(System.lineSeparator(),
                "1",             
                "TituloX",
                "99/99/9999",    
                "01/01/2024",    
                "Descripcion...",
                "99",            
                "1",             
                "n",             
                "7"
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Formato de fecha inválido"));
        assertTrue(output.contains("Opción inválida, inténtalo de nuevo."));
        assertTrue(output.contains("Momento vivído añadido correctamente."));
    }
}
