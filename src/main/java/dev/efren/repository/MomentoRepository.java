package dev.efren.repository;

import dev.efren.model.Momento;
import dev.efren.model.Emocion;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MomentoRepository {
    private final List<Momento> momentos = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final File storageFile;

    public MomentoRepository() {
        this("momentos.db");
    }

    public MomentoRepository(String storageFilePath) {
        this.storageFile = new File(storageFilePath);
        load();
    }

    public synchronized Momento add(String titulo, String descripcion, Emocion emocion, LocalDate fechaOcurrencia) {
        int id = idCounter.incrementAndGet();
        Momento m = new Momento(id, titulo, descripcion, emocion, fechaOcurrencia);
        momentos.add(m);
        persist();
        return m;
    }

    public synchronized List<Momento> findAll() {
        return new ArrayList<>(momentos);
    }

    public synchronized Optional<Momento> findById(int id) {
        return momentos.stream().filter(m -> m.getId() == id).findFirst();
    }

    public synchronized boolean deleteById(int id) {
        boolean removed = momentos.removeIf(m -> m.getId() == id);
        if (removed) persist();
        return removed;
    }

    public synchronized List<Momento> findByEmocion(Emocion e) {
        return momentos.stream()
                .filter(m -> m.getEmocion() == e)
                .collect(Collectors.toList());
    }

    public synchronized List<Momento> findByYearMonth(YearMonth ym) {
        return momentos.stream()
                .filter(m -> YearMonth.from(m.getFechaOcurrencia()).equals(ym))
                .collect(Collectors.toList());
    }

    private synchronized void persist() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(new ArrayList<>(momentos));
            oos.writeInt(idCounter.get());
        } catch (IOException e) {
            System.err.println("Error guardando momentos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private synchronized void load() {
        if (!storageFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            List<Momento> loaded = (List<Momento>) ois.readObject();
            int lastId = ois.readInt();
            momentos.clear();
            momentos.addAll(loaded);
            idCounter.set(lastId);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se pudo cargar el almacenamiento (se ignorará): " + e.getMessage());
        }
    }

    public synchronized void clear() {
        momentos.clear();
        idCounter.set(0);
        if (storageFile.exists()) {
            storageFile.delete();
        }
    }
}
