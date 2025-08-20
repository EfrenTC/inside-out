package dev.efren.model;

import java.time.LocalDate;

public class Pelicula {
    private String imdbId;
    private String titulo;
    private String generos;
    private String emocion; 
    private int releaseYear;
    private LocalDate fechaCreacion;

    public Pelicula(String imdbId, String titulo, String generos, String emocion, int releaseYear, LocalDate fechaCreacion) {
        this.imdbId = imdbId;
        this.titulo = titulo;
        this.generos = generos;
        this.emocion = emocion;
        this.releaseYear = releaseYear;
        this.fechaCreacion = fechaCreacion;
    }

    public String getImdbId() { return imdbId; }
    public String getTitulo() { return titulo; }
    public String getGeneros() { return generos; }
    public String getEmocion() { return emocion; }
    public int getReleaseYear() { return releaseYear; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    @Override
    public String toString() {
        return String.format("%s (%d) - Género: %s - Emoción: %s",
                titulo, releaseYear, generos, emocion);
    }
}
