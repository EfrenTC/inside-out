package dev.efren;

import dev.efren.controller.DiarioController;
import dev.efren.controller.PeliculaController;
import dev.efren.repository.MomentoRepository;
import dev.efren.repository.PeliculaRepository;
import dev.efren.view.ConsoleView;

public class App {
    public static void main(String[] args) {

        String storageMomentos = args.length > 0 ? args[0] : "momentos.db";
        MomentoRepository momentoRepo = new MomentoRepository(storageMomentos);
        DiarioController diarioController = new DiarioController(momentoRepo);

        String storagePeliculas = args.length > 1 ? args[1] : "peliculas.csv";
        PeliculaRepository peliculaRepo = new PeliculaRepository(storagePeliculas);
        PeliculaController peliculaController = new PeliculaController(peliculaRepo);

        ConsoleView view = new ConsoleView(diarioController, peliculaController);

        view.start();

        diarioController.exportToCSV("momentos.csv");
        System.out.println("CSV generado en momentos.csv");
    }
}
