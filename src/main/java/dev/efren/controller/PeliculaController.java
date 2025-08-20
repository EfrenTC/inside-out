package dev.efren.controller;

import dev.efren.model.Pelicula;
import dev.efren.repository.PeliculaRepository;

import java.time.LocalDate;
import java.util.List;

public class PeliculaController {
    private final PeliculaRepository repo;

    public PeliculaController(PeliculaRepository repo) {
        this.repo = repo;
    }

    public void addPelicula(String imdbId, String titulo, String generos, String emocion, int releaseYear) {
        Pelicula p = new Pelicula(imdbId, titulo, generos, emocion, releaseYear, LocalDate.now());
        repo.add(p);
    }

    public List<Pelicula> getAll() {
        return repo.findAll();
    }

    public void delete(String imdbId) {
        repo.deleteById(imdbId);
    }

    public List<Pelicula> getByGenero(String genero) {
        return repo.findByGenero(genero);
    }
}
