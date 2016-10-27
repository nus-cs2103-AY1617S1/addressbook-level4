package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CommandUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.ui.PersonListPanel;

//@@author A0139145E
/**
 * Sets as completed a task identified using it's last displayed index from the address book.
 */
public class DoneCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets as completed the task identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_TASK_ALREADY_DONE = "Task is already completed.";

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredDatedTaskList();
        UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList = model.getFilteredUndatedTaskList();
        
        if (!CommandUtil.isValidIndex(targetIndex, lastUndatedTaskList.size(), 
                lastShownList.size(), PersonListPanel.DATED_DISPLAY_INDEX_OFFSET)){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask readTaskToComplete;
        if (targetIndex > PersonListPanel.DATED_DISPLAY_INDEX_OFFSET) {
            readTaskToComplete = lastShownList.get(targetIndex - 1 - PersonListPanel.DATED_DISPLAY_INDEX_OFFSET);
        }
        else {
            readTaskToComplete = lastUndatedTaskList.get(targetIndex - 1);
        }

        if (!readTaskToComplete.getStatus().equals(new Status(Status.State.DONE))){
            try {
                model.completeTask(readTaskToComplete);
                
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be found";
            }
            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, readTaskToComplete));
        }
        else {
            return new CommandResult(String.format(MESSAGE_TASK_ALREADY_DONE));
        }
        //TODO look at posting a set as completed event.
        //EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));

    }

}
//@@author
