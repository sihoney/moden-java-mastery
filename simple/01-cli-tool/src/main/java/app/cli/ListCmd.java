package app.cli;

import app.App;
import app.command.ListAll;
import picocli.CommandLine.Command;

@Command(name = "list", description = "List all notes")
public class ListCmd implements Runnable {

    @Override
    public void run() {
        App.service().handle(new ListAll());
    }
}

