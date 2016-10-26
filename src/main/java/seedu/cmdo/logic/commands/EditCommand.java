package seedu.cmdo.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the task associated with the intended index.
 * 
 * @author A0139661Y
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the task residing at index input \n"
            + "Parameters: <index> <details> by/on <date> at <time> /<priority> /<TAG...>\n"
    		+ "NOTE: You must reenter all parameters again.\n\n"
            + "Example: " + COMMAND_WORD + " 2 Take Bongo out for a walk tomorrow 2pm /medium -dog";
    
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
        this.isUndoable = true;
    }
    
    public ReadOnlyTask getTask() {
        return toEditWith;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        // Check if target index is valid
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        // Retrieve the task and check if done.
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        if (taskToEdit.checkDone().value) {
            indicateAttemptToExecuteIncorrectCommand();
        	return new CommandResult(Messages.MESSAGE_EDIT_TASK_IS_DONE_ERROR);
        }
        
        try {
            model.editTask(taskToEdit, toEditWith);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
    	return new CommandResult(Messages.MESSAGE_EDIT_TASK_SUCCESS);
    }

}