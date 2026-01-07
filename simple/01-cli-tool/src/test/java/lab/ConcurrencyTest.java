package lab;

import app.command.Create;
import app.command.ListAll;
import app.persistence.FileStore;
import app.service.NoteService;

import java.time.Duration;
import java.util.concurrent.*;

public class ConcurrencyTest {

    public static void main(String[] args) throws Exception {

        var service = new NoteService(new FileStore());

        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            int n = i;
            pool.submit(() ->
                    service.handle(new Create("T" + n, "Data" + n, Duration.ofSeconds(10)))
            );
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        service.handle(new ListAll());
    }
}

