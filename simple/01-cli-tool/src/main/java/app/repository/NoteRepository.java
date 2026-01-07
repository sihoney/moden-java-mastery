package app.repository;

import app.model.Note;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// 상태 관리 전담

public class NoteRepository {

    private final ConcurrentHashMap<Long, Note> store = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong();

    public NoteRepository(Collection<Note> initial) {
        initial.forEach(n -> store.put(n.id(), n));
        id.set(store.keySet().stream().mapToLong(v -> v).max().orElse(0));
    }

    public Note create(String title, String content, Duration ttl) {
        long newId = id.incrementAndGet();
        Instant expiry = ttl == null ? null : Instant.now().plus(ttl);
        var note = new Note(newId, title, content, expiry);
        store.put(newId, note);
        return note;
    }

    public void evictExpired() {
        store.values().removeIf(Note::expired);
    }

    public void delete(long id) {
        store.remove(id);
    }

    public Collection<Note> findAll() {
        return store.values().stream()
                .filter(n -> !n.expired())
                .toList();
    }
}

