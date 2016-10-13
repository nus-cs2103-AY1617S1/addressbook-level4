package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.history.ReversibleEffect;
import seedu.address.model.TaskManager;
import seedu.address.model.item.Task;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    
    public static final String TOOL_TIP = "clear";

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        
        List<Task> affectedTasks = new ArrayList<Task>(model.getTaskManager().getUniqueTaskList().getInternalList());
        history.update(new ReversibleEffect(this.COMMAND_WORD, affectedTasks));
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
