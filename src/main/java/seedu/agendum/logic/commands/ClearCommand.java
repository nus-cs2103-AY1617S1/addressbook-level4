package seedu.agendum.logic.commands;

import seedu.agendum.model.ToDoList;

/**
 * Clears the to do list.
 */
public class ClearCommand extends Command {
    
    // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "clear";
    public static String COMMAND_FORMAT = "clear";
    public static String COMMAND_DESCRIPTION = "clear all tasks in Agendum";
    public static final String MESSAGE_SUCCESS = "Your tasks have been cleared!";

    public ClearCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(ToDoList.getEmptyToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getName() {
        return COMMAND_WORD;
    }

    @Override
    public String getFormat() {
        return COMMAND_FORMAT;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
