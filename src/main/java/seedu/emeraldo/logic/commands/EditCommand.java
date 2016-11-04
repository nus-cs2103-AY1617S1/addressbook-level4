package seedu.emeraldo.logic.commands;

import seedu.emeraldo.commons.core.EventsCenter;
import seedu.emeraldo.commons.core.Messages;
import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.events.ui.JumpToListRequestEvent;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.task.Description;
import seedu.emeraldo.model.task.DateTime;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139196U
/**
 * Edits a task in the task manager.
 */
public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD            
            + ": Edits the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: INDEX (must be a positive integer) + (one or more of the following) \"TASK_DESCRIPTION\""
            + " [on DATE] [by DATE_TIME] [from START_DATE_TIME] [to END_DATE_TIME]\n"
            + "Example: \n" + COMMAND_WORD + " 1" + " \"CS2103T Software Demo\"" + " by 7/11/2016 23:59\n"
            + COMMAND_WORD + " 4" + " \"Photoshop Camp\"" + " from 14 dec, 9am to 16 dec, 6pm";
   
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    public final int targetIndex;
    public final Description description;
    public final DateTime dateTime;
    
    public EditCommand(String targetIndex, String description, String completeDT) throws IllegalValueException {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.description = new Description(description);
        this.dateTime = new DateTime(completeDT);
    }
    
    //@@author A0139342H
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = (Task) lastShownList.get(targetIndex - 1);

        try {
            model.editTask(taskToEdit, description, dateTime);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));

    }

}
