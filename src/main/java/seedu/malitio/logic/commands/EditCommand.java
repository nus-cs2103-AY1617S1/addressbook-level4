package seedu.malitio.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from Malitio.
 * Only the attribute(s) that require changes is(are) entered.
 * @author Bel
 *
 */
public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Edits the name of the task task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) NAME\n"
            + "Example: " + COMMAND_WORD + " 1 New Name";
    
    public static final String MESSAGE_DUPLICATE_TASK = "The intended edit correspond to a pre-existing task in Malitio";
    
    private final int targetIndex;
    
    private final FloatingTask editedTask;
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Successfully edited task.\nOld: %1$s\nNew: %2$s";
    
/*    private String newName;
    
    private Start start;
    
    private End end;
    
    private By by;
    
    private Set<Tag> tagSet;
    
    private boolean isNameChanged = false;
    
    private boolean isStartChanged = false;
    
    private boolean isEndChanged = false;
    
    private boolean isByChanged = false;
    
    private boolean isTagsChanged = false;
    
*/    
    public EditCommand(int targetIndex, String name, Set<String> newTags) 
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : newTags) {
            tagSet.add(new Tag(tagName));
        }
        this.editedTask = new FloatingTask(
                new Name(name),
                new UniqueTagList(tagSet)
        );      
    }
    
/*
    public EditCommand(int targetIndex, String name, Start start, End end, By by, Set<String> newTags) {
        checkIsNameModified(name);
        checkIsStartModified(start);
        checkIsEndModified(end);
        checkIsByModified(by);
        checkIsTagsModified(newTags);
        
    }

    private void checkIsTagsModified(Set<String> newTags) throws IllegalValueException {
        if (newTags != null) {
            isTagsChanged = true;
            this.tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
        }
    }

    private void checkIsByModified(By by) {
        if (by!=null) {
            this.by = by;
            isByChanged = true;
        }
    }

    private void checkIsEndModified(End end) {
        if (end!=null) {
            this.end = end;
            isEndChanged = true;
        }
    }

    private void checkIsStartModified(Start start) {
        if (start!=null) {
            this.start = start;
            isStartChanged = true;
        }
    }

    private void checkIsNameModified(String name) {
        if (name!=null) {
            isNameChanged = true;
            this.newName = name;
        }
    }
 */
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownList = model.getFilteredFloatingTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyFloatingTask taskToEdit = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            model.addFloatingTask(editedTask);
            model.deleteTask(taskToEdit);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueFloatingTaskList.DuplicateFloatingTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask));
    }
    
}
