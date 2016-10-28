//@@author A0127855W
package seedu.flexitrack.logic.commands;

import static seedu.flexitrack.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;
import java.util.Stack;

import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.IllegalEditException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the FlexiTrack.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_SHORTCUT = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]"
            + ": Edits the specified task attributes of the task identified by the index number used in the last task listing.\n"
            + "Parameters to edit an event: [index] (must be a positive integer) from/ [starting time] to/ [ending time]\n"
            + "Example: " + COMMAND_WORD + " 1 " + "from/ 01062016 to/ 01/072016\n"
            + "Parameters to edit a task: [index] (must be a positive integer) by/ [due date]\n" + "Example: "
            + COMMAND_WORD + " 1 " + "by/ 01062016";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited: %1$s";
    
    public static final HashMap<String, Integer> EDIT_PARAMETER_PASSING_MASK = new HashMap<String, Integer>();
    static {
        EDIT_PARAMETER_PASSING_MASK.put("name", 0);
        EDIT_PARAMETER_PASSING_MASK.put("dueDate", 1);
        EDIT_PARAMETER_PASSING_MASK.put("startTime", 2);
        EDIT_PARAMETER_PASSING_MASK.put("endTime", 3);
    }

    public final int targetIndex;
    public final String[] arguments;

    private Task taskStore; 
    private Task editedTask;

    public EditCommand(int targetIndex, String[] arguments) {
        this.targetIndex = targetIndex;
        this.arguments = arguments;
    }
 
    /** 
     * Constructor for the undo method
     */
    public EditCommand() {
        this.targetIndex = 0 ;
        this.arguments = null;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        String duration = null; 

        try {
            taskStore = lastShownList.get(targetIndex - 1).copy(); 
            editedTask = model.editTask(lastShownList.get(targetIndex - 1), arguments);
            editedTask = editedTask.copy();
            recordCommand(this); 
        } catch (IndexOutOfBoundsException ioobe) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalEditException iee) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            assert false : "Illegal value entered";
        }
        
        if (editedTask.getIsEvent()) {
            duration =  DateTimeInfo.durationOfTheEvent(editedTask.getStartTime().toString(),
                    editedTask.getEndTime().toString());
        } else {
            duration = "";
        }
       
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, lastShownList.get(targetIndex - 1).getName())
                + "\n" + duration);
    }
    
    //@@author A0127686R
    @Override
    public void executeUndo() {
        Task toDelete = editedTask; 
        Task toAddBack = taskStore;

        try {
            model.deleteTask(toDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        try {
            model.addTask(toAddBack);
        } catch (DuplicateTaskException e) {
            indicateAttemptToExecuteIncorrectCommand();
        }
    }
}
