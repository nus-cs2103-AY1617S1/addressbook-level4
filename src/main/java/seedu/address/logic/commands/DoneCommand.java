package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String TOOL_TIP = "done INDEX";

    public static final String MESSAGE_DONE_ITEM_SUCCESS = "Archived Item: %1$s";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToArchive = lastShownList.get(targetIndex - 1);
        UnmodifiableObservableList<ReadOnlyTask> doneList = model.getFilteredDoneTaskList();
        model.addDoneTask(new Task(personToArchive));
        try {
            model.deleteTask(personToArchive);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DONE_ITEM_SUCCESS, personToArchive));
    }
}
