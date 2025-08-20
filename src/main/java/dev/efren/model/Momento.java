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
    private boolean esBueno; 

    public Momento(int id, String titulo, String descripcion, Emocion emocion, LocalDate fechaOcurrencia, boolean esBueno) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.emocion = emocion;
        this.fechaOcurrencia = fechaOcurrencia;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = fechaCreacion;
        this.esBueno = esBueno;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Emocion getEmocion() { return emocion; }
    public LocalDate getFechaOcurrencia() { return fechaOcurrencia; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public boolean isEsBueno() { return esBueno; }

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
    public void setEsBueno(boolean esBueno) {
        this.esBueno = esBueno;
        touch();
    }

    private void touch() {
        this.fechaModificacion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
            "%d. Ocurrió el: %s. Título: %s. Descripción: %s. Emoción: %s. Tipo: %s",
            id, fechaOcurrencia, titulo, descripcion, emocion, esBueno ? "Bueno" : "Malo"
        );
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
