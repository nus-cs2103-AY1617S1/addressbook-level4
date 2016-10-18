package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.ggist.model.task.UniqueTaskList.TaskTypeNotFoundException;
import seedu.ggist.model.task.*;

import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Edits task information
 * 
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Edits a task identified by the index number used in the last task listing. "
            + "Parameters: INDEX [FIELD TO CHANGE] [NEW INFO] \n\t" + "Example: " + COMMAND_WORD + " 1, task, Buy eggs";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_INVALID_TASK_TYPE = "%1$s is not a valid type";

    public int targetIndex;
    public String toEdit;
    public String type;

    public final String TASK_NAME_TYPE = "task";
    public final String DATE_TYPE = "date";
    public final String START_TIME_TYPE = "start time";
    public final String END_TIME_TYPE = "end time";

    /**
     * Convenience constructor using raw values.
     *
     */
    public EditCommand(int targetIndex, String type, String toEdit) {
        this.targetIndex = targetIndex;
        this.type = type;
        this.toEdit = toEdit;

    }

    @Override
    public CommandResult execute() {
        try {
            model.editTask(targetIndex, type, toEdit);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        } catch (TaskTypeNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_TASK_TYPE_NOT_IN_GGIST);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_Task_DISPLAYED_INDEX);
        }
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        try {
            model.editTask(taskToEdit);
            listOfCommands.push(COMMAND_WORD);
            listOfTasks.push(taskToEdit);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    public synchronized void editTask (int index, String type, String toEdit) throws TaskTypeNotFoundException {
        Task toBeEditedTask = filteredTasks.get(index-1);
        switch (type) {
        case "task":
            try{
                toBeEditedTask.getTaskName().editTaskName(toEdit);
            } catch (IllegalValueException ive) {
                System.out.printf(MESSAGE_INVALID_TASK_TYPE,type);
            }
            break;
        case "start date":
            try{
             toBeEditedTask.getStartDate().editDate(toEdit);
            } catch (IllegalValueException ive) {
                System.out.printf(MESSAGE_INVALID_TASK_TYPE,type);
            }
             break;
        case "start time":
            try{
            toBeEditedTask.getStartTime().editTime(toEdit);
            } catch (IllegalValueException ive) {
                System.out.printf(MESSAGE_INVALID_TASK_TYPE,type);
            }
            break;
        case "end date":
            try{
            toBeEditedTask.getEndTime().editTime(toEdit);
            } catch (IllegalValueException ive) {
                System.out.printf(MESSAGE_INVALID_TASK_TYPE,type);
            }
            break;
        case "end time":
            try{
            toBeEditedTask.getEndTime().editTime(toEdit);
            } catch (IllegalValueException ive) {
                System.out.printf(MESSAGE_INVALID_TASK_TYPE,type);
            }
            break;
        default:
            throw new TaskTypeNotFoundException();
        }
        model.updateFilteredListToShowChanges();
    }

}