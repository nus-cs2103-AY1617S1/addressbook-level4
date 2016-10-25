package seedu.taskitty.logic.commands;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Optional;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Edits the floating task identified by the index number used in the last task listing.\n "
            + "Parameters: NAME [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + Task.CATEGORY_CHARS
            + " 1 finish CS2103T";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    
    public final int categoryIndex;
    
    public final int targetIndex;

    private Task toEdit;
    ReadOnlyTask taskToEdit;
    private String[] data;
    private final Set<Tag> tagSet;
    
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

        assert categoryIndex >= 0 && categoryIndex < 3;
        
        this.targetIndex = targetIndex;
        this.categoryIndex = categoryIndex;
        this.data = data;
        tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
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

        taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            Optional<CommandResult> result = updateToEditVariable();
            if (result.isPresent()) {
                return result.get();
            }
            model.editTask(taskToEdit, toEdit);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            model.undo();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            model.removeUnchangedState();
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, Task.CATEGORIES[categoryIndex], toEdit));
    }
    
    /**
     * Ensure that toEdit variable has proper values before executing the command
     * @return
     * @throws IllegalValueException
     */
    private Optional<CommandResult> updateToEditVariable() throws IllegalValueException {
        if (data.length == Task.TASK_COMPONENT_COUNT) {
            this.toEdit = new Task(
                new Name(data[Task.TASK_COMPONENT_INDEX_NAME]),
                new TaskPeriod(),
                new UniqueTagList(tagSet)
            );
        } else if (data.length == Task.DEADLINE_COMPONENT_COUNT) {
            if (data[0].isEmpty()) {
                data[0] = taskToEdit.getName().toString();   
            }
            if (data[2] == null) {
                if (categoryIndex != 1) {
                    return Optional.of(new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
                }
                data[2] = taskToEdit.getPeriod().getEndTime().toString();
            }
            this.toEdit = new Task(
                new Name(data[Task.DEADLINE_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.DEADLINE_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.DEADLINE_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet)
            );
        } else if (data.length == Task.EVENT_COMPONENT_COUNT) {
            if (data[0].isEmpty()) {
                data[0] = taskToEdit.getName().toString();   
            }
            if (data[2] == null) {
                if (categoryIndex != 2) {
                    return Optional.of(new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
                }
                data[2] = taskToEdit.getPeriod().getStartTime().toString();
            }
            if (data[4] == null) {
                data[4] = taskToEdit.getPeriod().getEndTime().toString();
            }
            this.toEdit = new Task(
                new Name(data[Task.EVENT_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.EVENT_COMPONENT_INDEX_START_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_START_TIME]),
                        new TaskDate(data[Task.EVENT_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet)
            );
        };
        return Optional.empty();
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        model.saveState(commandText);
    }
}