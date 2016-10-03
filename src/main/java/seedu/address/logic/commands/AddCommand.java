package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.ToDo;

import java.util.HashSet;

/**
 * Adds a to-do to the to-do list
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    private final ToDo toDo;

    public AddCommand(String titleString) throws IllegalValueException {
        assert titleString != null;

        final Title title = new Title(titleString);
        toDo = new ToDo(title);
    }

    @Override
    public CommandResult execute() {
        model.addToDo(toDo);
        return new CommandResult(String.format(Messages.MESSAGE_TODO_ADDED, toDo));
    }
}
