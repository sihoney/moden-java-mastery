package app.cli;

import picocli.CommandLine.Command;

@Command(name = "lab", subcommands = {
        CreateCmd.class,
        DeleteCmd.class,
        ListCmd.class
})
public class RootCommand implements Runnable {
    public void run() {
        System.out.println("Use a subcommand.");
    }
}
