package seedu.todo.logic;

import seedu.todo.logic.commands.BaseCommand;

public interface Dispatcher {
    public BaseCommand dispatch();
}
