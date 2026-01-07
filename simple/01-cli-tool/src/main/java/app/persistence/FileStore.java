package app.persistence;

import app.model.Note;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class FileStore {

    private final Path path = Paths.get("notes.db");

    public FileStore() {
        try {
            if (Files.notExists(path)) Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize storage", e);
        }
    }

    public synchronized List<Note> load() {
        try {
            return Files.readAllLines(path).stream()
                    .filter(line -> !line.isBlank())
                    .map(this::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load notes", e);
        }
    }

    public synchronized void save(List<Note> notes) {
        var lines = notes.stream()
                .map(this::format)
                .collect(Collectors.toList());

        try {
            Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save notes", e);
        }
    }

    // 포맷: id|title|content|expiresAtEpochMilli(or empty)
    private Note parse(String line) {
        var parts = line.split("\\|", 4);
        long id = Long.parseLong(parts[0]);
        String title = parts[1];
        String content = parts[2];

        Instant expiresAt = null;
        if (parts.length >= 4 && !parts[3].isBlank()) {
            long epochMilli = Long.parseLong(parts[3]);
            expiresAt = Instant.ofEpochMilli(epochMilli);
        }

        return new Note(id, title, content, expiresAt);
    }

    private String format(Note n) {
        String exp = (n.expiresAt() == null) ? "" : String.valueOf(n.expiresAt().toEpochMilli());
        return n.id() + "|" + n.title() + "|" + n.content() + "|" + exp;
    }
}
