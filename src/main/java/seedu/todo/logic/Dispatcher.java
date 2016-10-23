package seedu.todo.logic;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.BaseCommand;

//@@author A0135817B
public interface Dispatcher {
    BaseCommand dispatch(String command) throws IllegalValueException;
}
