package seedu.oneline.logic.commands;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * Edits a task to the task book.
 */
public class EditTaskCommand extends EditCommand {

    public final int targetIndex;
    private final Map<TaskField, String> fields;
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task to the task book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";


    public EditTaskCommand(int targetIndex, Map<TaskField, String> fields) throws IllegalValueException, IllegalCmdArgsException {
        this.targetIndex = targetIndex;
        this.fields = fields;
    }
    
    public static EditTaskCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        Entry<Integer, Map<TaskField, String>> info = Parser.getIndexAndTaskFieldsFromArgs(args);
        int targetIndex = info.getKey();
        Map<TaskField, String> fields = info.getValue();
        return new EditTaskCommand(targetIndex, fields);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask oldTask = lastShownList.get(targetIndex - 1);
        Task newTask = null;
        try {
            newTask = oldTask.update(fields);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
        try {
            model.replaceTask(oldTask, newTask);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newTask));
        } catch (UniqueTaskList.TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "The update task should not already exist";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newTask.toString()));
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}
