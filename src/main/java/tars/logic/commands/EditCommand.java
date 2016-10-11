package tars.logic.commands;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.*;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import java.time.DateTimeException;
import java.util.HashMap;


/**
 * Edits a task identified using it's last displayed index from tars.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit a task in tars. "
            + "Parameters: INDEX (must be a positive integer) -n NAME -dt DATETIME -p PRIORITY "
            + "-ta TAGTOADD -tr TAGTOREMOVE\n"
            + "Example: " + COMMAND_WORD
            + " 1 -n Lunch with John -dt 10/09/2016 1200 to 10/09/2016 1300 -p l -ta lunch -tr dinner";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";

    private static final String MESSAGE_MISSING_TASK = "The target task cannot be missing";
        
    public final int targetIndex;

    private HashMap<Flag, String> argsToEdit;
    
    /**
     * Convenience constructor using raw values.
     */
    public EditCommand(int targetIndex, HashMap<Flag, String> argsToEdit) {
        this.targetIndex = targetIndex;
        this.argsToEdit = argsToEdit;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask toEdit = lastShownList.get(targetIndex - 1);

        try {
            Task editedTask = model.editTask(toEdit, this.argsToEdit);
            toEdit = editedTask;
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(MESSAGE_MISSING_TASK);
        } catch (DateTimeException dte) {
            return new CommandResult(Messages.MESSAGE_INVALID_DATE);
        } catch (IllegalValueException | TagNotFoundException e) {
            return new CommandResult(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
    }

}
