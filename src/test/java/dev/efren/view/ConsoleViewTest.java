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
                "2",              
                "5"                
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
                "5"     
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
                "5"     
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("A")); 
    }

    @Test
    void testInvalidMenuOption() {
        String input = String.join(System.lineSeparator(),
                "9",  
                "5"   
        ) + System.lineSeparator();

        provideInput(input);
        ConsoleView view = new ConsoleView(controller);
        view.start();

        String output = getOutput();
        assertTrue(output.contains("Opción no válida."));
        assertTrue(output.contains("Hasta la próxima!!!"));
    }
}
