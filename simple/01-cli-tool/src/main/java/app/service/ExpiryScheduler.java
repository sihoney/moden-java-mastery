package app.service;

import app.repository.NoteRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExpiryScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ExpiryScheduler(NoteRepository repo) {
//        sheduleAtFixedRate: 일정한 간격으로 작업을 반복하는 간단한 타이버 역할
        scheduler.scheduleAtFixedRate(
                repo::evictExpired,
                5,
                5,
                TimeUnit.SECONDS
        );
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
