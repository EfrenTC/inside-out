package dev.efren.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Momento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String titulo;
    private String descripcion;
    private Emocion emocion;
    private LocalDate fechaOcurrencia;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public Momento(int id, String titulo, String descripcion, Emocion emocion, LocalDate fechaOcurrencia) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.emocion = emocion;
        this.fechaOcurrencia = fechaOcurrencia;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = fechaCreacion;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Emocion getEmocion() { return emocion; }
    public LocalDate getFechaOcurrencia() { return fechaOcurrencia; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        touch();
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        touch();
    }
    public void setEmocion(Emocion emocion) {
        this.emocion = emocion;
        touch();
    }
    public void setFechaOcurrencia(LocalDate fechaOcurrencia) {
        this.fechaOcurrencia = fechaOcurrencia;
        touch();
    }

    private void touch() {
        this.fechaModificacion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("%d. Ocurrió el: %s. Título: %s. Descripción: %s. Emoción: %s",
                id, fechaOcurrencia, titulo, descripcion, emocion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Momento momento = (Momento) o;
        return id == momento.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
