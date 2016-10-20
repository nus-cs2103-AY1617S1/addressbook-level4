package seedu.address.logic.commands;

import seedu.address.model.TaskScheduler;
import seedu.address.model.Undo;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task scheduler has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        Undo undo = new Undo(COMMAND_WORD);
        for (ReadOnlyTask task : model.getTaskScheduler().getTaskList()) {
            undo.addTask((Task) task);
        }
        model.resetData(TaskScheduler.getEmptyTaskScheduler());
        CommandHistory.addMutateCmd(undo);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
