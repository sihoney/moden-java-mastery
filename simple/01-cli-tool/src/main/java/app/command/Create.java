package app.command;

import java.time.Duration;

public record Create(String title, String content, Duration ttl) implements Command {
    public Create {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Title required");
        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Content required");
        if (ttl != null && (ttl.isZero() || ttl.isNegative()))
            throw new IllegalArgumentException("TTL must be positive");
    }
}
