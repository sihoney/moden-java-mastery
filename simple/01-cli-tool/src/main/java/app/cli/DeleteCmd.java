package app.cli;

import app.App;
import app.command.Delete;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "delete", description = "Delete a note by id")
public class DeleteCmd implements Runnable {

    @Option(names = "--id", required = true, description = "Note id")
    long id;

    @Override
    public void run() {
        App.service().handle(new Delete(id));
    }
}

