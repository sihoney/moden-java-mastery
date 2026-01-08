package app;

import app.cli.RootCommand;
import app.persistence.FileStore;
import app.service.NoteService;
import picocli.CommandLine;

public class App {

    private static final NoteService service = new NoteService(new FileStore());

    public static NoteService service() {
        return service;
    }

    public static void main(String[] args) {
        new CommandLine(new RootCommand()).execute(args);
    }
}
