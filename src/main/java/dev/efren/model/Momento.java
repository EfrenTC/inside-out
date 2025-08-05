package dev.efren.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import dev.efren.utils.Emocion; 

public class Momento {
    private static int contadorId = 1;

    private int id;
    private String titulo;
    private String descripcion;
    private Emocion emocion;
    private LocalDate fechaMomento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public Momento(String titulo, String descripcion, Emocion emocion, LocalDate fechaMomento) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.emocion = emocion;
        this.fechaMomento = fechaMomento;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Emocion getEmocion() {
        return emocion;
    }

    public LocalDate getFechaMomento() {
        return fechaMomento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.fechaModificacion = LocalDateTime.now();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.fechaModificacion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nFecha: " + fechaMomento +
               "\nTítulo: " + titulo +
               "\nDescripción: " + descripcion +
               "\nEmoción: " + emocion +
               "\n";
    }
}
