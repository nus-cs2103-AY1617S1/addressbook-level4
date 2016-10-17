package seedu.tasklist.logic.commands;

import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.Task;
/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Your to-do list has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
      	Task.IncompleteCounter=0;
    	Task.floatCounter=0;
    	Task.overdueCounter=0;
        model.resetData(TaskList.getEmptyTaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
