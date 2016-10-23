package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.TagNotFoundException;
import seedu.task.model.task.*;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Updates a task in the task list.
 */
public class UpdateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "update";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update a task in the task list.\n "
            + "Parameters: INDEX (must be a positive integer) [NAME t/TAGADD rt/TAGREMOVE]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 cs2103 t/quiz";
    
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s"; 
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Rollback changes to updated task!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    
    public final int targetIndex;
    
    private final Name newTaskName;
    private final DateTime newOpenTime;
    private final DateTime newCloseTime;
    private final Set<String> removedTags;
    private final UniqueTagList newTaskTags;
    
    public UpdateCommand(int targetIndex, String name, String openTime, String closeTime, Set<String> tagsToAdd, Set<String> tagsToRemove) throws IllegalValueException { 
        this.targetIndex = targetIndex;
        this.newTaskName = (name.isEmpty()) ? null : new Name(name);
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsToAdd) {
            tagSet.add(new Tag(tagName));
        }
        this.newOpenTime = new DateTime(openTime);
        this.newCloseTime = new DateTime(closeTime);
        
        this.newTaskTags = new UniqueTagList(tagSet);
        this.removedTags = tagsToRemove;
    }
    
    private void mergeTaskTags(ReadOnlyTask taskToUpdate) {
        newTaskTags.mergeFrom(taskToUpdate.getTags());
        
        for (String tagName : removedTags) {
            try {
                newTaskTags.remove(new Tag(tagName));
            } catch (TagNotFoundException e) {
                // do nothing, as update is more lenient
            } catch (IllegalValueException e) {
                assert false : "Tag cannot be of other type!";
            }
        }
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(false, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        Name updatedTaskName = (newTaskName == null) ? taskToUpdate.getName() : newTaskName;

        DateTime updatedOpenTime = (newOpenTime.isEmpty()) ? taskToUpdate.getOpenTime() : newOpenTime;
        DateTime updatedCloseTime = (newCloseTime.isEmpty()) ? taskToUpdate.getCloseTime() : newCloseTime;
        mergeTaskTags(taskToUpdate);
        
        Task newTask; 
        try {
            newTask = new Task(
                    updatedTaskName,
                    updatedOpenTime,
                    updatedCloseTime,
                    taskToUpdate.getImportance(),
                    taskToUpdate.getComplete(),
                    newTaskTags,
                    taskToUpdate.getRecurrentWeek()
            );
        } catch (IllegalValueException e1) {
            return new CommandResult(false, e1.getMessage()); 
        }
        
        assert model != null;
        try {
            model.updateTask(taskToUpdate, newTask);
        } catch (DuplicateTaskException e) {
            return new CommandResult(false, MESSAGE_DUPLICATE_TASK);
        }

        return new CommandResult(true, String.format(MESSAGE_UPDATE_TASK_SUCCESS, newTask));
    }

    @Override
    public CommandResult rollback() {
        assert model != null;
        model.rollback();
        
        return new CommandResult(true, String.format(MESSAGE_ROLLBACK_SUCCESS));
    }
}
