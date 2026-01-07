package app.persistence;

import app.model.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// FileWriterWorker -> 비동기 저장 전담

public class FileWriterWorker {

//    ExecutorService: thread pool 관리자
//    newSingleThreadExecutor: "작업 큐(내부 BlockingQueue) + worker thread 1개"로 구성
    private final ExecutorService writer = Executors.newSingleThreadExecutor();
    private final FileStore store;

    public FileWriterWorker(FileStore store) {
        this.store = store;
//        Executor thread는 non-daemon
//        non-daemon thread가 하나라도 살아 있으면 JVM은 종료되지 않는다.
//        shutdown()으로 생명주기 관리!
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void persistAsync(Collection<Note> notes) {
//        직렬 IO 처리
        writer.submit(() -> store.save(new ArrayList<>(notes)));
    }

    public void shutdown() {
        writer.shutdown();
        try {
            if (!writer.awaitTermination(3, TimeUnit.SECONDS)) {
                writer.shutdownNow();
            }
        } catch (InterruptedException e) {
            writer.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

