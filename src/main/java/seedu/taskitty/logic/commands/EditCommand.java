package seedu.taskitty.logic.commands;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.taskitty.commons.core.LogsCenter;
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

public class EditCommand extends Command {
    
    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);


    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " <index> [new name] [new datetime]";
    public static final String MESSAGE_USAGE = "This command edits a task in TasKitty, Meow!"
            + "\n<index> is the index eg. t1, d1, e1.";
    
    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    public final int categoryIndex;

    public final int targetIndex;

    private Task toEdit;
    private ReadOnlyTask taskToEdit;
    private String[] data;
    private Set<Tag> tagSet;
    private final String commandText;
    
    private final int NAME_INDEX = 0;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public EditCommand(String[] data, Set<String> tags, int targetIndex, int categoryIndex, 
            String commandText) throws IllegalValueException {

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

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = 
                AppUtil.getCorrectListBasedOnCategoryIndex(model, categoryIndex);
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(targetIndex - 1);
        logger.info("Task to be edited: " + taskToEdit.getAsText());

        try {
            Optional<CommandResult> result = updateToEditVariable();
            if (isInvalidResult(result)) {
                return result.get();
            }
            logger.info("New edited task: " + toEdit.getAsText());
            model.editTask(taskToEdit, toEdit);
            model.updateToDefaultList();
            model.storeCommandInfo(COMMAND_WORD, commandText, toEdit, taskToEdit);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, Task.CATEGORIES[categoryIndex] + targetIndex, 
                toEdit));
    }

    /**
     * Ensure that toEdit variable has proper values before executing the command.
     * 
     * @return
     * @throws IllegalValueException
     */
    private Optional<CommandResult> updateToEditVariable() throws IllegalValueException {
        setTagsToExistingTagsIfTagsNotEdited();
        
        if (data.length == Task.TASK_COMPONENT_COUNT) {
            createNewEditedTodo();
        } else if (data.length == Task.DEADLINE_COMPONENT_COUNT) {
            setTaskNameToExistingTaskNameIfTaskNameNotEdited();
            Optional<CommandResult> invalidResult = setDeadlineEndTimeToExistingEndTimeIfEndTimeNotEdited();
            if (isInvalidResult(invalidResult)) {
                return invalidResult;
            }
            createNewEditedDeadline();
            markAsOverdueIfTaskToEditIsOverdue();
        } else if (data.length == Task.EVENT_COMPONENT_COUNT) {
            setTaskNameToExistingTaskNameIfTaskNameNotEdited();
            Optional<CommandResult> invalidResult = setEventStartTimeToExistingStartTimeIfStartTimeNotEdited();
            if (isInvalidResult(invalidResult)) {
                return invalidResult;
            };
            setEventEndTimeToExistingEndTimeIfEndTimeNotEdited();
            createNewEditedEvent();
        }
        markAsDoneIfTaskToEditIsDone();
        return emptyOptional();
    }

    /**
     * Checks if the Optional parameter is an invalid result
     * @param invalidResult
     * @return true if Optional is an invalid result (ie Optional is not empty)
     */
    private boolean isInvalidResult(Optional<CommandResult> invalidResult) {
        return invalidResult.isPresent();
    }

    // ================ Setter methods ==============================
    
    /**
     * Sets event start time to the existing event start time if user did not input any start time.
     * 
     * @return emptyOptional() if task to be edited is a todo or a deadline. If users wish to change edit
     * a todo or a deadline to an event, then users must specify the start time and end time.
     * In this case, nothing will be edited.
     */
    private Optional<CommandResult> setEventStartTimeToExistingStartTimeIfStartTimeNotEdited() {
        if (data[Task.EVENT_COMPONENT_INDEX_START_TIME] == null) {
            if (categoryIndex != Task.EVENT_CATEGORY_INDEX) {
                return Optional.of(new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        Command.MESSAGE_FORMAT + MESSAGE_PARAMETER)));
            }
            data[Task.EVENT_COMPONENT_INDEX_START_TIME] = taskToEdit.getPeriod().getStartTime().toString();
        }
        return emptyOptional();
    }

    /**
     * Sets deadline end time to the existing deadline end time if user did not input any end time.
     * 
     * @return emptyOptional() if task to be edited is a todo or an event. If users wish to change edit
     * a todo or an event to a deadline, then users must specify the end time. 
     * In this case, nothing will be edited.
     */
    private Optional<CommandResult> setDeadlineEndTimeToExistingEndTimeIfEndTimeNotEdited() {
        if (data[Task.DEADLINE_COMPONENT_INDEX_END_TIME] == null) {
            if (categoryIndex != Task.DEADLINE_CATEGORY_INDEX) {
                return Optional.of(new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        Command.MESSAGE_FORMAT + MESSAGE_PARAMETER)));
            }
            data[Task.DEADLINE_COMPONENT_INDEX_END_TIME] = taskToEdit.getPeriod().getEndTime().toString();
        }
        return emptyOptional();
    }

    /**
     * Sets task name to existing task name if user did not input any task name to edit.
     */
    private void setTaskNameToExistingTaskNameIfTaskNameNotEdited() {
        if (data[NAME_INDEX].isEmpty()) {
            data[NAME_INDEX] = taskToEdit.getName().toString();
        }
    }
    
    /**
     * Sets event end time to existing event end time if user did not input any end time.
     */
    private void setEventEndTimeToExistingEndTimeIfEndTimeNotEdited() {
        if (data[Task.EVENT_COMPONENT_INDEX_END_TIME] == null) {
            data[Task.EVENT_COMPONENT_INDEX_END_TIME] = taskToEdit.getPeriod().getEndTime().toString();
        }
    }

    /**
     * Sets tags to existing tags if user did not input any tags to edit.
     */
    private void setTagsToExistingTagsIfTagsNotEdited() {
        if (tagSet.isEmpty()) {
            tagSet = taskToEdit.getTags().toSet();
        }
    }
    
    // ================ Task creator methods ==============================
    
    /**
     * Returns a new edited Todo Task
     * 
     * @throws IllegalValueException if any of the values are invalid
     */
    private void createNewEditedTodo() throws IllegalValueException {
        assert data.length == Task.TASK_COMPONENT_COUNT;
        
        this.toEdit = new Task(new Name(data[Task.TASK_COMPONENT_INDEX_NAME]), new TaskPeriod(),
                new UniqueTagList(tagSet));
    }
    
    /**
     * Returns a new edited Deadline Task
     * 
     * @throws IllegalValueException if any of the values are invalid
     */
    private void createNewEditedDeadline() throws IllegalValueException {
        assert data.length == Task.DEADLINE_COMPONENT_COUNT;
        
        this.toEdit = new Task(new Name(data[Task.DEADLINE_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.DEADLINE_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.DEADLINE_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet));
    }
    
    /**
     * Returns a new edited Event Task
     * 
     * @throws IllegalValueException if any of the values are invalid
     */
    private void createNewEditedEvent() throws IllegalValueException {
        assert data.length == Task.EVENT_COMPONENT_COUNT;
        
        this.toEdit = new Task(new Name(data[Task.EVENT_COMPONENT_INDEX_NAME]),
                new TaskPeriod(new TaskDate(data[Task.EVENT_COMPONENT_INDEX_START_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_START_TIME]),
                        new TaskDate(data[Task.EVENT_COMPONENT_INDEX_END_DATE]),
                        new TaskTime(data[Task.EVENT_COMPONENT_INDEX_END_TIME])),
                new UniqueTagList(tagSet));
    }

    // ================ Marker methods ==============================
    
    /**
     * Marks edited task as done if original task if already marked as done.
     */
    private void markAsDoneIfTaskToEditIsDone() {
        if (taskToEdit.getIsDone()) {
            this.toEdit.markAsDone();
        }
    }
    
    /**
     * Marks edited task as overdue if original task if already marked as overdue.
     */
    private void markAsOverdueIfTaskToEditIsOverdue() {
        if (DateTimeUtil.isOverdue(this.toEdit)) {
            this.toEdit.markAsOverdue();
        }
    }

    private Optional<CommandResult> emptyOptional() {
        return Optional.empty();
    }

}