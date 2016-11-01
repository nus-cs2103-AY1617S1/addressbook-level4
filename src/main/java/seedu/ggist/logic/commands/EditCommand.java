package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.ggist.model.task.*;

import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Edits task information
 * 
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Edits a task identified by the index number used in the last task listing. \n"
            + "Parameters: INDEX [FIELD TO CHANGE] [NEW INFO] \n" + "Example: " + COMMAND_WORD + " 1 task, buy eggs";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited: %1$s";

    public int targetIndex;
    public String[] field;
    public String[] value;

    /**
     * Convenience constructor using raw values.
     *
     */
    public EditCommand(int targetIndex, String[] field, String[] value) {
        this.targetIndex = targetIndex;
        this.field = field;
        this.value = value;

    }

    // @@author A0138420N
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        Task taskToEdit = (Task) lastShownList.get(targetIndex - 1);
        
        for (int i = 0; i < field.length; i++) {
            boolean correctField = field[i].equals("task") || field[i].equals("start date") || field[i].equals("end date")
                    || field[i].equals("start time") || field[i].equals("end time") || field[i].equals("priority");
            if (!correctField) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_FIELD);
            }

            
            try {
                model.getEditTaskField().push(field[i]);

                if (field[i].equals("task")) {
                    model.getEditTaskValue().push(taskToEdit.getTaskName().toString());
                } else if (field[i].equals("start date")) {
                    model.getEditTaskValue().push(taskToEdit.getStartDate().toString());
                } else if (field[i].equals("end date")) {
                    model.getEditTaskValue().push(taskToEdit.getEndDate().toString());
                } else if (field[i].equals("start time")) {
                    model.getEditTaskValue().push(taskToEdit.getStartTime().toString());
                } else if (field[i].equals("end time")) {
                    model.getEditTaskValue().push(taskToEdit.getEndTime().toString());
                } else if (field[i].equals("priority")) {
                    model.getEditTaskValue().push(taskToEdit.getPriority().toString());
                }
                model.editTask(taskToEdit, field[i], value[i]);
                model.getListOfCommands().push(COMMAND_WORD);
                model.getListOfTasks().push(taskToEdit);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            } catch (IllegalValueException ive) {
                return new CommandResult(ive.getMessage());
            }
            indicateCorrectCommandExecuted();
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}
// @@author