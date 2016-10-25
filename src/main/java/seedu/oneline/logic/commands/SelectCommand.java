package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.events.ui.JumpToListRequestEvent;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the task book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    
    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public static SelectCommand createFromArgs(String args) throws IllegalValueException {
        Integer index = null;
        try {
            index = Parser.getIndexFromArgs(args);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        if (index == null) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        return new SelectCommand(index);
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

}
