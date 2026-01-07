package app.model;

import java.time.Instant;

public record Note(
        long id,
        String title,
        String content,
        Instant expiresAt
) {
    public boolean expired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }
}
