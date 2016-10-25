package seedu.oneline.logic.commands;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * Adds a task to the task book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. \n"
            + "Parameters: <taskName> [.from <start> .to <end>] [.due <deadline>] [.every <period>] [#<cat>] \n"
            + "Example: " + COMMAND_WORD
            + " Acad meeting .from 2pm .to 4pm #acad";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";

    private final Task toAdd;

    public AddCommand(String args) throws IllegalValueException, IllegalCmdArgsException {
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        TaskName newName = new TaskName(fields.get(TaskField.NAME));
        TaskTime newStartTime = fields.containsKey(TaskField.START_TIME) ? 
                                    new TaskTime(fields.get(TaskField.START_TIME)) :
                                    TaskTime.getDefault();
        TaskTime newEndTime = fields.containsKey(TaskField.END_TIME) ? 
                                    new TaskTime(fields.get(TaskField.END_TIME)) :
                                    TaskTime.getDefault();
        TaskTime newDeadline = fields.containsKey(TaskField.DEADLINE) ? 
                                    new TaskTime(fields.get(TaskField.DEADLINE)) :
                                    TaskTime.getDefault();
        TaskRecurrence newRecurrence = fields.containsKey(TaskField.RECURRENCE) ? 
                                    new TaskRecurrence(fields.get(TaskField.RECURRENCE)) :
                                        TaskRecurrence.getDefault();
        Set<String> tags = fields.containsKey(TaskField.TAG_ARGUMENTS) ?
                                    Parser.getTagsFromArgs(fields.get(TaskField.TAG_ARGUMENTS)) :
                                    new HashSet<String>();
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }                 
        
        this.toAdd = new Task(
                newName,
                newStartTime,
                newEndTime,
                newDeadline,
                newRecurrence,
                new UniqueTagList(tagSet),
                false
        );
    }
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime, String deadline, String recurrence, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new TaskName(name),
                new TaskTime(startTime),
                new TaskTime(endTime),
                new TaskTime(deadline),
                new TaskRecurrence(recurrence),
                new UniqueTagList(tagSet),
                false
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
