package seedu.tasklist.logic.commands;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.events.ui.JumpToListRequestEvent;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the task list.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: \n" + COMMAND_WORD + " 1\n" + COMMAND_WORD + " 2";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    private int targetIndex;
    
    public SelectCommand() {};
    
    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

}
