package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.*;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    //@@author A0139930B
    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " [name] [start] to [end] [#tag]...";
    public static final String MESSAGE_USAGE = "This command adds a task to TasKitty, Meow!";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;
    private final String commandText;

    /**
     * Convenience constructor using values parsed from Natty
     *
     * @throws IllegalValueException if data is invalid
     */
    public AddCommand(String[] data, Set<String> tags, String commandText) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        this.commandText = commandText;
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = createNewTaskFromData(data, tagSet);
    }
    
    /**
     * Returns a newly created Task based on the input data.
     * 
     * @throws IllegalValueException if any of the values are invalid or there are too many inputs
     */
    private Task createNewTaskFromData(String[] data, Set<Tag> tagSet) throws IllegalValueException {
        Task newTask;
        
        if (data.length == Task.TASK_COMPONENT_COUNT) {
            newTask = createNewTodoTask(data, tagSet);
        } else if (data.length == Task.DEADLINE_COMPONENT_COUNT) {
            newTask = createNewDeadlineTask(data, tagSet);
        } else if (data.length == Task.EVENT_COMPONENT_COUNT) {
            newTask = createNewEventTask(data, tagSet);
        } else {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    MESSAGE_FORMAT + MESSAGE_PARAMETER));
        }
        
        return newTask;
    }
    
    /**
     * Returns a newly created Todo Task
     * Todo Tasks only have name, tags
     * 
     * @throws IllegalValueException if any of the values are invalid
     */
    private Task createNewTodoTask(String[] data, Set<Tag> tagSet) throws IllegalValueException {
        assert data.length == Task.TASK_COMPONENT_COUNT;
        
        return new Task(new Name(data[Task.TASK_COMPONENT_INDEX_NAME]),
            new TaskPeriod(),
            new UniqueTagList(tagSet));
    }
    
    /**
     * Returns a newly created Deadline Task
     * Deadline Tasks only have name, endDate, endTime, tags
     * 
     * @throws IllegalValueException if any of the values are invalid
     */
    private Task createNewDeadlineTask(String[] data, Set<Tag> tagSet) throws IllegalValueException {
        assert data.length == Task.DEADLINE_COMPONENT_COUNT;
        
        return new Task(new Name(data[Task.DEADLINE_COMPONENT_INDEX_NAME]),
            new TaskPeriod(new TaskDate(data[Task.DEADLINE_COMPONENT_INDEX_END_DATE]),
                new TaskTime(data[Task.DEADLINE_COMPONENT_INDEX_END_TIME])),
            new UniqueTagList(tagSet));
    }
    
    /**
     * Returns a newly created Event Task
     * Event Tasks have name, startDate, startTime, endDate, endTime, tags
     * 
     * @throws IllegalValueException if any of the values are invalid
     */
    private Task createNewEventTask(String[] data, Set<Tag> tagSet) throws IllegalValueException {
        assert data.length == Task.EVENT_COMPONENT_COUNT;
        
        return new Task(new Name(data[Task.EVENT_COMPONENT_INDEX_NAME]),
            new TaskPeriod(new TaskDate(data[Task.EVENT_COMPONENT_INDEX_START_DATE]),
                new TaskTime(data[Task.EVENT_COMPONENT_INDEX_START_TIME]),
                new TaskDate(data[Task.EVENT_COMPONENT_INDEX_END_DATE]),
                new TaskTime(data[Task.EVENT_COMPONENT_INDEX_END_TIME])),
            new UniqueTagList(tagSet));
    }

    //@@author
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.updateToDefaultList();
            model.storeCommandInfo(COMMAND_WORD, commandText, toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
