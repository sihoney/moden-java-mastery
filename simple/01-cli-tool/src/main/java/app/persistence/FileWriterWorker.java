package app.persistence;

import app.model.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// FileWriterWorker -> 비동기 저장 전담

public class FileWriterWorker {

//    newSingleThreadExecutor: "작업 큐(내부 BlockingQueue) + worker thread 1개"로 구성
    private final ExecutorService writer = Executors.newSingleThreadExecutor();
    private final FileStore store;

    public FileWriterWorker(FileStore store) {
        this.store = store;
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
//        non-daemon thread가 하나라도 살아 있으면 JVM은 종료되지 않는다.
    }

    public void persistAsync(Collection<Note> notes) {
//        직렬 IO 처리
        writer.submit(() -> store.save(new ArrayList<>(notes)));
    }

    public void shutdown() {
//        shutdown(): 부드러운 종류, 이미 진행 중인 작업은 끝까지 수행 & 새로운 작업만 막음
        writer.shutdown();
        try {
//            awaitTermination: 실행 중인 작업이 종료될 때까지 (최대 3초) block
            if (!writer.awaitTermination(3, TimeUnit.SECONDS)) {
//                shutdownNow(): 즉시 중단 시도, 현재 진행 중인 작업들에게 인터럽트를 걸어서 즉시 중단을 시도
                writer.shutdownNow();
            }
        } catch (InterruptedException e) {
            writer.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

