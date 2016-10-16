package seedu.todo.logic;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.parser.ParseResult;

public interface Dispatcher {
    public BaseCommand dispatch(String command) throws IllegalValueException;
}
