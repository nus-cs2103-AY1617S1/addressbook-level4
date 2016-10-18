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

/**
 * 
 * @author zexuan
 *
 * Edits an existing task/event in Jimi.
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Edits an existing task/event in Jimi. \n"
            + "Parameters: INDEX(must be a positive integer) EDITS_TO_MAKE\n" 
            + "Example: " + COMMAND_WORD + " 2 \"clear trash\"\n"
            + "> Tip: Typing `e`, `ed`, `edi` instead of `edit` works too.";
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Updated task details: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Jimi";
    
    private final int taskIndex; //index of task/event to be edited
    private UniqueTagList newTagList;
    private Name newName;
    
    public EditCommand() {
        taskIndex = 0;
    }
    
    /**
     * Convenience constructor using raw values.
     * //TODO: change to support FloatingTask, DeadlineTask and Events types as well
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
        if (name.length() != 0) {
            this.newName = new Name(name);
        }
        if (!tagSet.isEmpty()) {
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
        ReadOnlyTask taskToReplace = new FloatingTask(
                newName == null ? taskToEdit.getName() : newName,
                newTagList == null ? taskToEdit.getTags() : newTagList);
        
        model.editReadOnlyTask(taskIndex - 1, new FloatingTask(taskToReplace));
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToReplace));
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
}
