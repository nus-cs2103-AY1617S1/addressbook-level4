package seedu.tasklist.logic.commands;

import seedu.tasklist.logic.parser.CommandParser;
import seedu.tasklist.model.TaskList;

/**
 * Clears the task list.
 */
public class ClearCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskList.getEmptyTaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public Command prepare(String args) {
        return new ClearCommand();
    }
}
