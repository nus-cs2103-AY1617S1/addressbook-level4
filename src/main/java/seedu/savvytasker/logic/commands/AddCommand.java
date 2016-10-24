package seedu.savvytasker.logic.commands;

import java.util.LinkedList;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.logic.commands.models.AddCommandModel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Savvy Tasker. "
            + "Parameters: TASK_NAME [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL]"
            + "[r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD
            + " Project Meeting s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103 d/Discuss about roles and milestones";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an add command.
     */
    public AddCommand(AddCommandModel commandModel) {
        final boolean isArchived = false;   // all tasks are first added as active tasks
        final int taskId = 0;               // taskId to be assigned by ModelManager, leave as 0
        this.toAdd = new Task(taskId, commandModel.getTaskName(),
                commandModel.getStartDateTime(), commandModel.getEndDateTime(),
                commandModel.getLocation(), commandModel.getPriority(),
                commandModel.getRecurringType(), commandModel.getNumberOfRecurrence(),
                commandModel.getCategory(), commandModel.getDescription(), isArchived);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Undo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        
        UnmodifiableObservableList<Task> lastShownList = model.getFilteredTaskListTask();
        
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i) == toAdd){
                ReadOnlyTask taskToDelete = lastShownList.get(i);
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } 
        return false;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }
}
