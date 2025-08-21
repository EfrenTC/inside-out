package dev.efren.controller;

import dev.efren.model.Pelicula;
import dev.efren.repository.PeliculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PeliculaControllerTest {

    private PeliculaRepository repo;
    private PeliculaController controller;

    @BeforeEach
    void setUp() {
        repo = mock(PeliculaRepository.class);
        controller = new PeliculaController(repo);
    }

    @Test
    void addPelicula_ShouldCreateAndSavePelicula() {
        ArgumentCaptor<Pelicula> captor = ArgumentCaptor.forClass(Pelicula.class);

        controller.addPelicula("tt123", "Inception", "Sci-Fi", "ALEGRIA", 2010);

        verify(repo, times(1)).add(captor.capture());
        Pelicula saved = captor.getValue();

        assertEquals("tt123", saved.getImdbId());
        assertEquals("Inception", saved.getTitulo());
        assertEquals("Sci-Fi", saved.getGeneros());
        assertEquals("ALEGRIA", saved.getEmocion());
        assertEquals(2010, saved.getReleaseYear());
    }

    @Test
    void getAll_ShouldReturnListFromRepository() {
        List<Pelicula> peliculas = List.of(new Pelicula("tt001", "Pelicula", "Drama", "TRISTEZA", 1999, java.time.LocalDate.now()));
        when(repo.findAll()).thenReturn(peliculas);

        List<Pelicula> result = controller.getAll();

        assertEquals(1, result.size());
        assertEquals("tt001", result.get(0).getImdbId());
        verify(repo, times(1)).findAll();
    }

    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        controller.delete("tt999");
        verify(repo, times(1)).deleteById("tt999");
    }

    @Test
    void getByGenero_ShouldReturnFilteredList() {
        List<Pelicula> comedias = List.of(new Pelicula("tt777", "Funny Movie", "Comedy", "ALEGRIA", 2005, java.time.LocalDate.now()));
        when(repo.findByGenero("Comedy")).thenReturn(comedias);

        List<Pelicula> result = controller.getByGenero("Comedy");

        assertEquals(1, result.size());
        assertEquals("Funny Movie", result.get(0).getTitulo());
        verify(repo, times(1)).findByGenero("Comedy");
    }
}
