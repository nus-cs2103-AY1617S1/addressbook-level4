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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a floating task to the task manager. "
            + "Parameters: NAME [DATE] [START_TIME] [END_TIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " finish CS2103T t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String[] data, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        if (data.length == Task.TASK_COMPONENT_COUNT) {
            this.toAdd = new Task(
                new Name(data[Task.TASK_COMPONENT_INDEX_NAME]),
                new UniqueTagList(tagSet)
            );
        } else if (data.length == Task.DEADLINE_COMPONENT_COUNT) {
            this.toAdd = new Task(
                new Name(data[Task.DEADLINE_COMPONENT_INDEX_NAME]),
                new TaskDate(data[Task.DEADLINE_COMPONENT_INDEX_END_DATE]),
                new TaskTime(data[Task.DEADLINE_COMPONENT_INDEX_END_TIME]),
                new UniqueTagList(tagSet)
            );
        } else if (data.length == Task.EVENT_COMPONENT_COUNT) {
            this.toAdd = new Task(
                new Name(data[Task.EVENT_COMPONENT_INDEX_NAME]),
                new TaskDate(data[Task.EVENT_COMPONENT_INDEX_START_DATE]),
                new TaskTime(data[Task.EVENT_COMPONENT_INDEX_START_TIME]),
                new TaskDate(data[Task.EVENT_COMPONENT_INDEX_END_DATE]),
                new TaskTime(data[Task.EVENT_COMPONENT_INDEX_END_TIME]),
                new UniqueTagList(tagSet)
            );
        } else {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            model.removeUnchangedState();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public void saveStateIfNeeded() {
        model.saveState();
    }

}
