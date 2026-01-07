package app.command;


public record Delete(long id) implements Command {
    public Delete {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id");
    }
}
