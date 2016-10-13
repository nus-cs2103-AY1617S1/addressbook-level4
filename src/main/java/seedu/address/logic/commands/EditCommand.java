package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Reminder;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the indexed task from Lifekeeper. \n"
            + "Parameters: INDEX (must be a positive integer) [n/TASK_NAME] [c/CATEGORY] [d/DEADLINE] p/PRIORITY_LEVEL r/REMINDER [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 n/CS2103 T8A2 d/15-10-2016 p/3 r/12-01-2016 t/CS t/project";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task from: %1$s\n to: %2$s";
    
    public static final String MESSAGE_TASK_EXISTS = "An existing task already contains the specified parameters.";
    
    public final int targetIndex;
    
    public final Task newParams;
    
    private Task editedTask;
    
    /**
     * Set parameters to null if they are not provided.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String name, String duedate, String priority, String reminder, Set<String> tags)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.newParams = new Task(
                new TaskName(name),
                new DueDate(duedate),
                new Priority(priority),
                new Reminder(reminder),
                new UniqueTagList(tagSet)
        );
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
            ReadOnlyTask oldTask = new Task(taskToEdit);
            
            editedTask = new Task(model.editTask(taskToEdit, newParams));
            
            PreviousCommand editCommand = new PreviousCommand(COMMAND_WORD,oldTask,editedTask);
            PreviousCommandsStack.push(editCommand);
            
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task to be edited cannot be missing";
        } catch (DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_TASK_EXISTS);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask));
    }

}
