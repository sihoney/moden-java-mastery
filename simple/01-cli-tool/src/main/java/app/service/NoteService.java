package app.service;

import app.command.Command;
import app.command.Create;
import app.command.Delete;
import app.command.ListAll;
import app.persistence.FileStore;
import app.persistence.FileWriterWorker;
import app.repository.NoteRepository;

public class NoteService {

    private final NoteRepository repo;
    private final FileWriterWorker writer;
    private final ExpiryScheduler scheduler;

    public NoteService(FileStore store) {
        var initial = store.load();
        this.repo = new NoteRepository(initial);
        this.writer = new FileWriterWorker(store);
        this.scheduler = new ExpiryScheduler(repo);
    }

//    synchronized X -> lock 최소화
    public void handle(Command cmd) {
        switch (cmd) {
            case Create c -> {
                repo.create(c.title(), c.content(), c.ttl());
                writer.persistAsync(repo.findAll());
            }
            case Delete d -> {
                repo.delete(d.id());
                writer.persistAsync(repo.findAll());
            }
            case ListAll l -> repo.findAll().forEach(System.out::println);
        }
    }

    public void shutdown() {
        scheduler.shutdown();
        writer.shutdown();
    }
}
