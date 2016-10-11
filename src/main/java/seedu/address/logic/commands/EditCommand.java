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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the selected task from Lifekeeper. Irreversible. "
            + "Parameters: INDEX (must be a positive integer) [TASK_NAME] [c/CATEGORY] [d/DEADLINE] p/PRIORITY_LEVEL r/REMINDER [t/TAG]...\n";
            /*+ "Example: " + COMMAND_WORD
            + " CS2103 T7A1 d/06-10-2016 p/1 r/05-01-2016 t/CS t/groupwork";*/
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    
    public static final String MESSAGE_TASK_EXISTS = "An existing task already contains the specified parameters.";
    
    public final int targetIndex;
    
    public final Task newParams;
    
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
            model.editTask(taskToEdit, newParams);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task to be edited cannot be missing";
        } catch (DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_TASK_EXISTS);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        } catch (Exception e) {
            //I'm pretty sure the code will not reach here.
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}
