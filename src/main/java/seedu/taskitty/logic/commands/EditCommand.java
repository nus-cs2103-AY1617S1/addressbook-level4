package seedu.taskitty.logic.commands;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.Name;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskPeriod;
import seedu.taskitty.model.task.TaskTime;
import seedu.taskitty.model.task.UniqueTaskList;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0135793W
/**
 * Edits a task identified using it's last displayed index from the task manager.
 */

public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " [category] [index] [name] [t/tag]...";
    public static final String MESSAGE_USAGE = "This command edits a task in TasKitty, Meow!";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    
    public final int categoryIndex;
    
    public final int targetIndex;

    private final Task toEdit;
    
    public EditCommand(String[] data, Set<String> tags, int targetIndex) 
            throws IllegalValueException {
        this(data, tags, targetIndex, Task.DEFAULT_CATEGORY_INDEX);
    }
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String[] data, Set<String> tags, int targetIndex, int categoryIndex)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.categoryIndex = categoryIndex;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (data.length == Task.TASK_COMPONENT_COUNT) {
            this.toEdit = new Task(
                new Name(data[Task.TASK_COMPONENT_INDEX_NAME]),
                new TaskPeriod(),
                new UniqueTagList(tagSet)
            );
        } else if (data.length == Task.DEADLINE_COMPONENT_COUNT) {
            this.toEdit = new Task(
                new Name(data[Task.DEADLINE_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.DEADLINE_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.DEADLINE_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet)
            );
        } else if (data.length == Task.EVENT_COMPONENT_COUNT) {
            this.toEdit = new Task(
                new Name(data[Task.EVENT_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.EVENT_COMPONENT_INDEX_START_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_START_TIME]),
                        new TaskDate(data[Task.EVENT_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet)
            );
        } else {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }
    
    @Override
    public CommandResult execute() {
        assert categoryIndex >= 0 && categoryIndex < 3;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = AppUtil.getCorrectListBasedOnCategoryIndex(model,categoryIndex);
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            model.removeUnchangedState();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            model.editTask(taskToEdit, toEdit);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            model.removeUnchangedState();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            model.removeUnchangedState();
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, Task.CATEGORIES[categoryIndex], toEdit));
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }
}