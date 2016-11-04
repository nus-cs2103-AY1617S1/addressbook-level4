package seedu.taskitty.logic.commands;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.commons.util.DateTimeUtil;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.Name;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskPeriod;
import seedu.taskitty.model.task.TaskTime;
import seedu.taskitty.model.task.UniqueTaskList;

//@@author A0135793W
/**
 * Edits a task identified using it's last displayed index from the task manager.
 */

public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " [index] [name] [#tag]...";
    public static final String MESSAGE_USAGE = "This command edits a task in TasKitty, Meow!"
            + "\n[index] is the index eg. t1, d1, e1.";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    
    public final int categoryIndex;
    
    public final int targetIndex;

    private Task toEdit;
    private ReadOnlyTask taskToEdit;
    private String[] data;
    private Set<Tag> tagSet;
    private final String commandText;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String[] data, Set<String> tags, int targetIndex, int categoryIndex, String commandText)
            throws IllegalValueException {

        assert categoryIndex >= 0 && categoryIndex < 3;
        
        this.targetIndex = targetIndex;
        this.categoryIndex = categoryIndex;
        this.data = data;
        this.commandText = commandText;
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
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            Optional<CommandResult> result = updateToEditVariable();
            if (result.isPresent()) {
                return result.get();
            }
            model.editTask(taskToEdit, toEdit);
            model.storeCommandInfo(COMMAND_WORD, commandText, toEdit, taskToEdit);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, Task.CATEGORIES[categoryIndex] + targetIndex, toEdit));
    }
    
    /**
     * Ensure that toEdit variable has proper values before executing the command
     * @return
     * @throws IllegalValueException
     */
    private Optional<CommandResult> updateToEditVariable() throws IllegalValueException {
        if (tagSet.isEmpty()) {
            tagSet = taskToEdit.getTags().toSet();
        }
        if (data.length == Task.TASK_COMPONENT_COUNT) {
            this.toEdit = new Task(
                new Name(data[Task.TASK_COMPONENT_INDEX_NAME]),
                new TaskPeriod(),
                new UniqueTagList(tagSet)
            );
            if (taskToEdit.getIsDone()) {
                this.toEdit.markAsDone();
            }
        } else if (data.length == Task.DEADLINE_COMPONENT_COUNT) {
            if (data[Task.DEADLINE_COMPONENT_INDEX_NAME].isEmpty()) {
                data[Task.DEADLINE_COMPONENT_INDEX_NAME] = taskToEdit.getName().toString();   
            }
            if (data[Task.DEADLINE_COMPONENT_INDEX_END_TIME] == null) {
                if (categoryIndex != Task.DEADLINE_CATEGORY_INDEX) {
                    return Optional.of(new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
                }
                data[Task.DEADLINE_COMPONENT_INDEX_END_TIME] = taskToEdit.getPeriod().getEndTime().toString();
            }
            this.toEdit = new Task(
                new Name(data[Task.DEADLINE_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.DEADLINE_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.DEADLINE_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet)
            );
            if (taskToEdit.getIsDone()) {
                this.toEdit.markAsDone();
            } else if (DateTimeUtil.isOverdue(this.toEdit)) {
                this.toEdit.markAsOverdue();
            }
        } else if (data.length == Task.EVENT_COMPONENT_COUNT) {
            if (data[Task.EVENT_COMPONENT_INDEX_NAME].isEmpty()) {
                data[Task.EVENT_COMPONENT_INDEX_NAME] = taskToEdit.getName().toString();   
            }
            if (data[Task.EVENT_COMPONENT_INDEX_START_TIME] == null) {
                if (categoryIndex != Task.EVENT_CATEGORY_INDEX) {
                    return Optional.of(new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)));
                }
                data[Task.EVENT_COMPONENT_INDEX_START_TIME] = taskToEdit.getPeriod().getStartTime().toString();
            }
            if (data[Task.EVENT_COMPONENT_INDEX_END_TIME] == null) {
                data[Task.EVENT_COMPONENT_INDEX_END_TIME] = taskToEdit.getPeriod().getEndTime().toString();
            }
            this.toEdit = new Task(
                new Name(data[Task.EVENT_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.EVENT_COMPONENT_INDEX_START_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_START_TIME]),
                        new TaskDate(data[Task.EVENT_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet)
            );
            if (taskToEdit.getIsDone()) {
                this.toEdit.markAsDone();
            }
        }
        return emptyOptional();
    }

    private Optional<CommandResult> emptyOptional() {
        return Optional.empty();
    }

}