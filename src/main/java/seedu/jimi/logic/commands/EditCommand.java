package seedu.jimi.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.exceptions.IllegalValueException;
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
            + "2 by 10th July at 12 pm";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Updated task details: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Jimi";

    private final int taskIndex; //index of task/event to be edited
    private UniqueTagList newTagList;
    private Name newName; 

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
        
        //if new fields are to be edited, instantiate them
        if(name != null) {
            this.newName = new Name(name);
        }
        if(tagSet != null) {
            this.newTagList = new UniqueTagList(tagSet);
        }
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(taskIndex - 1);
        
        try {
            model.deleteTask(taskToEdit); //delete 
            
            UniqueTagList oldTagList = taskToEdit.getTags();
            Name oldName = taskToEdit.getName();
            
            if(newName != null && !oldName.equals(newName)){
                if(newTagList != null && !oldTagList.equals(newTagList)){
                    model.addFloatingTask(new FloatingTask(newName, newTagList)); //change both name and tags
                }
                model.addFloatingTask(new FloatingTask(newName, oldTagList)); //change only name
            }
            else {
                model.addFloatingTask(new FloatingTask(oldName, oldTagList)); //change nothing //TODO: reduce redundancy
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, lastShownList.get(lastShownList.size()-1)));
    }

}
