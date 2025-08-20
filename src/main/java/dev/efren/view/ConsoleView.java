package dev.efren.view;

import dev.efren.controller.DiarioController;
import dev.efren.controller.PeliculaController;
import dev.efren.model.Emocion;
import dev.efren.model.Momento;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ConsoleView {
    private final DiarioController controller;
    private final PeliculaController peliculaController;
    private final java.util.Scanner sc = new java.util.Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleView(DiarioController controller, PeliculaController peliculaController) {
        this.controller = controller;
        this.peliculaController = peliculaController;
    }

    public ConsoleView(DiarioController controller) {
        this.controller = controller;
        this.peliculaController = null;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String opt = prompt("Seleccione una opción: ");
            switch (opt) {
                case "1" -> addMomento();
                case "2" -> viewAll();
                case "3" -> deleteMomento();
                case "4" -> filterMenu();
                case "5" -> exportCSV();
                case "6" -> peliculasMenu();
                case "7" -> {
                    System.out.println("\nHasta la próxima!!!");
                    running = false;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMy diario:");
        System.out.println("1. Añadir momento");
        System.out.println("2. Ver todos los momentos disponibles");
        System.out.println("3. Eliminar un momento");
        System.out.println("4. Filtrar los momentos");
        System.out.println("5. Exportar momentos a CSV");
        System.out.println("6. Gestión de películas");
        System.out.println("7. Salir");
    }

    private void addMomento() {
        String titulo = prompt("Ingrese el título: ");
        LocalDate fecha = readDate("Ingresa la fecha (dd/mm/yyyy): ");
        String descripcion = prompt("Ingrese la descripción: ");
        Emocion emocion = selectEmocion();
        boolean esBueno = prompt("¿Es un momento bueno? (s/n): ").equalsIgnoreCase("s");

        controller.addMomento(titulo, descripcion, emocion, fecha, esBueno);
        System.out.println("Momento vivído añadido correctamente.");
    }

    private void viewAll() {
        System.out.println("\nLista de momentos vividos:");
        List<Momento> all = controller.getAll();
        if (all.isEmpty()) {
            System.out.println("No hay momentos registrados.");
            return;
        }
        all.forEach(System.out::println);
    }

    private void deleteMomento() {
        String idStr = prompt("Ingresa el identificador del momento: ");
        try {
            int id = Integer.parseInt(idStr);
            boolean ok = controller.delete(id);
            System.out
                    .println(ok ? "Momento vivído eliminado correctamente." : "No se encontró un momento con ese id.");
        } catch (NumberFormatException e) {
            System.out.println("Id inválido.");
        }
    }

    private void filterMenu() {
        System.out.println("\nFiltar por ...:");
        System.out.println("1. Emoción");
        System.out.println("2. Fecha");
        System.out.println("3. Solo momentos buenos");
        System.out.println("4. Solo momentos malos");
        String opt = prompt("Ingrese una opción: ");
        switch (opt) {
            case "1" -> filterByEmocion();
            case "2" -> filterByFecha();
            case "3" -> printMomentList(controller.getBuenos());
            case "4" -> printMomentList(controller.getMalos());
            default -> System.out.println("Opción inválida.");
        }
    }

    private void filterByEmocion() {
        Emocion e = selectEmocion();
        List<Momento> list = controller.getByEmocion(e);
        printMomentList(list);
    }

    private void filterByFecha() {
        LocalDate fecha = readDate("Ingrese la fecha (dd/mm/yyyy): ");
        YearMonth ym = YearMonth.from(fecha);
        List<Momento> list = controller.getByYearMonth(ym);
        printMomentList(list);
    }

    private void exportCSV() {
        String filePath = prompt("Ingrese el nombre del archivo CSV (ej: momentos.csv): ");
        boolean ok = controller.exportToCSV(filePath);
        if (ok) {
            System.out.println(" Momentos exportados correctamente a " + filePath);
        } else {
            System.out.println(" Error al exportar momentos.");
        }
    }

    private void printMomentList(List<Momento> list) {
        System.out.println("\nLista de momentos vividos:");
        if (list.isEmpty()) {
            System.out.println("No hay resultados.");
            return;
        }
        list.forEach(System.out::println);
    }

    private Emocion selectEmocion() {
        Emocion[] values = Emocion.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf("%d. %s%n", i + 1, values[i]);
        }
        while (true) {
            String opt = prompt("Ingrese su opción: ");
            try {
                int idx = Integer.parseInt(opt);
                return Emocion.fromOrdinal(idx);
            } catch (IllegalArgumentException e) {
                System.out.println("Opción inválida, inténtalo de nuevo.");
            }
        }
    }

    private LocalDate readDate(String promptText) {
        while (true) {
            String ds = prompt(promptText);
            try {
                return LocalDate.parse(ds, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Usa dd/MM/yyyy.");
            }
        }
    }

    private String prompt(String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    
    private void peliculasMenu() {
        while (true) {
            System.out.println("\n--- Gestión de Películas ---");
            System.out.println("1. Registrar película");
            System.out.println("2. Listar películas");
            System.out.println("3. Filtrar por género");
            System.out.println("4. Eliminar película");
            System.out.println("0. Volver al menú principal");
            String opt = prompt("Seleccione una opción: ");

            switch (opt) {
                case "1" -> {
                    System.out.print("IMDB Id: ");
                    String imdbId = sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Géneros (separados por '|'): ");
                    String generos = sc.nextLine();
                    System.out.print("Emoción: ");
                    String emocion = sc.nextLine();
                    System.out.print("Año de estreno: ");
                    int releaseYear = Integer.parseInt(sc.nextLine());

                    peliculaController.addPelicula(imdbId, titulo, generos, emocion, releaseYear);
                    System.out.println("Película guardada en CSV.");
                }
                case "2" -> peliculaController.getAll().forEach(System.out::println);
                case "3" -> {
                    System.out.print("Género a filtrar: ");
                    String genero = sc.nextLine();
                    peliculaController.getByGenero(genero).forEach(System.out::println);
                }
                case "4" -> {
                    System.out.print("IMDB Id de la película a eliminar: ");
                    String id = sc.nextLine();
                    peliculaController.delete(id);
                    System.out.println("Película eliminada.");
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
