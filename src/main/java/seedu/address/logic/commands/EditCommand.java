package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Detail;
import seedu.address.model.task.DueByDate;
import seedu.address.model.task.DueByTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the task associated with the intended index.
 * 
 * @author A0139661Y
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the task residing at index input "
            + "Parameters: <index>\n"
            + "Example: " + COMMAND_WORD + " 2";
    
    public static final String MESSAGE_EDITED_PERSON_SUCCESS = "Edited Task: %1$s";

    private final int targetIndex;
    private final Task toEditWith;

    public EditCommand(int targetIndex,
    					String newDetail,
    					LocalDate newDueByDate,
    					LocalTime newDueByTime,
    					String newPriority,
    					Set<String> newTags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : newTags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEditWith = new Task(
                new Detail(newDetail),
                new DueByDate (newDueByDate),
                new DueByTime(newDueByTime),
                new Priority(newPriority),
                new UniqueTagList(tagSet)
        );
        
        this.targetIndex = targetIndex;
    }
    
    public ReadOnlyTask getTask() {
        return toEditWith;
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
            model.editTask(taskToEdit, toEditWith);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
    	return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}