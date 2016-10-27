package seedu.agendum.logic.commands;

import seedu.agendum.model.ToDoList;

/**
 * Clears the to do list.
 */
public class ClearCommand extends Command {
    
    // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_FORMAT = "clear";
    public static final String COMMAND_DESCRIPTION = "clear all tasks in Agendum";
    public static final String MESSAGE_SUCCESS = "Your tasks have been cleared!";

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ToDoList.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
