package dev.efren.view;

import dev.efren.controller.DiarioController;
import dev.efren.model.Emocion;
import dev.efren.model.Momento;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final DiarioController controller;
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleView(DiarioController controller) {
        this.controller = controller;
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
                case "5" -> {
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
        System.out.println("5. Salir");
    }

    private void addMomento() {
        String titulo = prompt("Ingrese el título: ");
        LocalDate fecha = readDate("Ingresa la fecha (dd/mm/yyyy): ");
        String descripcion = prompt("Ingrese la descripción: ");
        Emocion emocion = selectEmocion();
        controller.addMomento(titulo, descripcion, emocion, fecha);
        System.out.println("Momento vivído añadido correctamente.");
    }

    private void viewAll() {
        System.out.println("\nLista de momentos vividos:");
        List<Momento> all = controller.getAll();
        if (all.isEmpty()) {
            System.out.println("No hay momentos registrados.");
            return;
        }
        all.forEach(m -> System.out.println(m.toString()));
    }

    private void deleteMomento() {
        String idStr = prompt("Ingresa el identificador del momento: ");
        try {
            int id = Integer.parseInt(idStr);
            boolean ok = controller.delete(id);
            System.out.println(ok ? "Momento vivído eliminado correctamente." : "No se encontró un momento con ese id.");
        } catch (NumberFormatException e) {
            System.out.println("Id inválido.");
        }
    }

    private void filterMenu() {
        System.out.println("\nFiltar por ...:");
        System.out.println("1. Emoción");
        System.out.println("2. Fecha");
        String opt = prompt("Ingrese una opción: ");
        switch (opt) {
            case "1" -> filterByEmocion();
            case "2" -> filterByFecha();
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

    private void printMomentList(List<Momento> list) {
        System.out.println("\nLista de momentos vividos:");
        if (list.isEmpty()) {
            System.out.println("No hay resultados.");
            return;
        }
        list.forEach(m -> System.out.println(m.toString()));
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
}
