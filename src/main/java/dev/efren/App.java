package dev.efren;

import dev.efren.controller.DiarioController;
import dev.efren.repository.MomentoRepository;
import dev.efren.view.ConsoleView;

public class App {
    public static void main(String[] args) {
        String storage = args.length > 0 ? args[0] : "momentos.db";
        MomentoRepository repo = new MomentoRepository(storage);
        DiarioController controller = new DiarioController(repo);
        ConsoleView view = new ConsoleView(controller);
        view.start();
    }
}
