package seedu.jimi.logic.commands;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.ModelManager;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;
import seedu.jimi.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * 
 * @author zexuan
 *
 * Edits an existing task/event in Jimi.
 */
public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task/event in Jimi. \n"
            + "Example: " + COMMAND_WORD
            + " 2 by 10th July at 12 pm";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Updated task details: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Jimi";

    private final int taskIndex; //index of task/event to be edited
    private UniqueTagList newTagList;
    private Name newName; 
    private UnmodifiableObservableList<ReadOnlyTask> lastShownList;

    /**
     * Convenience constructor using raw values.
     * //TODO: change to support FloatingTask, Task and Events types as well
     * 
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String name, Set<String> tags, int taskIndex) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.taskIndex = taskIndex;
        
        lastShownList = model.getFilteredTaskList();
        ReadOnlyTask taskToEdit = lastShownList.get(taskIndex - 1);
        
        //if new fields are to be edited, instantiate them, else set them to the oldName
        if(name.length() != 0) {
            this.newName = new Name(name);
        }
        else {
            this.newName = taskToEdit.getName(); //assigns old task name
        }
        
        if(!tagSet.isEmpty()) {
            this.newTagList = new UniqueTagList(tagSet);
        }
        else {
            this.newTagList = taskToEdit.getTags(); //assigns old tag list
        }
    }
    @Override
    public CommandResult execute() {
        if (lastShownList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask taskToReplace = new FloatingTask(newName, newTagList);
        
        ((ModelManager) model).editFloatingTask(new FloatingTask(taskToReplace), taskIndex - 1);
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToReplace));
    }
}
