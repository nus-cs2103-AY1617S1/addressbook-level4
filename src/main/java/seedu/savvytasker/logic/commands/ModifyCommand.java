package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.logic.commands.models.ModifyCommandModel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Adds a person to the address book.
 */
public class ModifyCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "modify";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Madifies a task in Savvy Tasker. "
            + "Parameters: INDEX [t/TASK_NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL]"
            + "[r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD
            + " 1 t/Project Meeting s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103 d/Discuss about roles and milestones";

    public static final String MESSAGE_SUCCESS = "Task modified: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final ModifyCommandModel commandModel;
    private Task originalTask;
    
    /**
     * Creates an add command.
     */
    public ModifyCommand(ModifyCommandModel commandModel) {
        this.commandModel = commandModel;
        this.originalTask = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert commandModel != null;

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < commandModel.getIndex()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToModify = lastShownList.get(commandModel.getIndex() - 1);
        Task replacement = new Task(taskToModify, commandModel);

        try {
            originalTask = (Task)taskToModify;
            model.modifyTask(taskToModify, replacement);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, replacement));
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
        execute();
        return false;
    }
    
    /**
     * Undo the add command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {

        assert model != null;
        assert commandModel != null;

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ReadOnlyTask taskToModify = lastShownList.get(commandModel.getIndex() - 1);

        try {
            model.modifyTask(taskToModify, originalTask);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
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
