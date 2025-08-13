package dev.efren.controller;

import dev.efren.model.Emocion;
import dev.efren.model.Momento;
import dev.efren.repository.MomentoRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;


public class DiarioController {
    private final MomentoRepository repo;

    public DiarioController(MomentoRepository repo) {
        this.repo = repo;
    }

    public Momento addMomento(String titulo, String descripcion, Emocion emocion, LocalDate fechaOcurrencia) {
        return repo.add(titulo, descripcion, emocion, fechaOcurrencia);
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
}
