package dev.efren.controller;

import dev.efren.model.Emocion;
import dev.efren.model.Momento;
import dev.efren.repository.MomentoRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DiarioController {
    private final MomentoRepository repo;

    public DiarioController(MomentoRepository repo) {
        this.repo = repo;
    }

    public Momento addMomento(String titulo, String descripcion, Emocion emocion, LocalDate fechaOcurrencia, boolean esBueno) {
        return repo.add(titulo, descripcion, emocion, fechaOcurrencia, esBueno);
    }

    public List<Momento> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }

    public List<Momento> getByEmocion(Emocion e) {
        return repo.findByEmocion(e);
    }

    public List<Momento> getByYearMonth(YearMonth ym) {
        return repo.findByYearMonth(ym);
    }

    public Optional<Momento> getById(int id) {
        return repo.findById(id);
    }

    public List<Momento> getBuenos() {
        return repo.findAll().stream()
                .filter(Momento::isEsBueno)
                .collect(Collectors.toList());
    }

    public List<Momento> getMalos() {
        return repo.findAll().stream()
                .filter(m -> !m.isEsBueno())
                .collect(Collectors.toList());
    }

    public boolean exportToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
        
            writer.append("ID,Titulo,Descripcion,Emocion,Fecha,EsBueno\n");

            for (Momento m : repo.findAll()) {
                writer.append(m.getId() + ",")
                        .append("\"" + m.getTitulo() + "\",")
                        .append("\"" + m.getDescripcion() + "\",")
                        .append(m.getEmocion().name() + ",")
                        .append(m.getFechaOcurrencia().toString() + ",")
                        .append(m.isEsBueno() ? "Bueno" : "Malo")
                        .append("\n");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar a CSV: " + e.getMessage());
            return false;
        }
    }
    
}
