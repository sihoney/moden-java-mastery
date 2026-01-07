package app.service;

import app.repository.NoteRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExpiryScheduler {

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    public ExpiryScheduler(NoteRepository repo) {
        scheduler.scheduleAtFixedRate(
                repo::evictExpired,
                5, 5, TimeUnit.SECONDS
        );
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
