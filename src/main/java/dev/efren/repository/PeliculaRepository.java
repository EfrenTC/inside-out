package dev.efren.repository;

import dev.efren.model.Pelicula;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PeliculaRepository {
    private final Path filePath;

    public PeliculaRepository(String fileName) {
        this.filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
                Files.write(filePath, "imdbId,titulo,generos,emocion,releaseYear,fechaCreacion\n".getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error creando archivo CSV", e);
            }
        }
    }

    public void add(Pelicula p) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            writer.write(String.format("%s,%s,%s,%s,%d,%s\n",
                    p.getImdbId(), p.getTitulo(), p.getGeneros(), p.getEmocion(),
                    p.getReleaseYear(), p.getFechaCreacion()));
        } catch (IOException e) {
            throw new RuntimeException("Error guardando película", e);
        }
    }

    public List<Pelicula> findAll() {
        try {
            return Files.lines(filePath)
                    .skip(1) 
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new Pelicula(
                                parts[0],
                                parts[1],
                                parts[2],
                                parts[3],
                                Integer.parseInt(parts[4]),
                                LocalDate.parse(parts[5])
                        );
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo películas", e);
        }
    }

    public void deleteById(String imdbId) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> filtered = lines.stream()
                    .filter(line -> !line.startsWith(imdbId + ","))
                    .collect(Collectors.toList());
            Files.write(filePath, filtered);
        } catch (IOException e) {
            throw new RuntimeException("Error eliminando película", e);
        }
    }

    public List<Pelicula> findByGenero(String genero) {
        return findAll().stream()
                .filter(p -> Arrays.asList(p.getGeneros().split("\\|")).contains(genero))
                .collect(Collectors.toList());
    }
}
