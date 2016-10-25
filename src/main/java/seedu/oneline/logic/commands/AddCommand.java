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

    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime, String deadline, String recurrence, String tag)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        this.toAdd = new Task(
                new TaskName(name),
                new TaskTime(startTime),
                new TaskTime(endTime),
                new TaskTime(deadline),
                new TaskRecurrence(recurrence),
                Tag.getTag(tag)
        );
    }
    
  //@@author A0140156R
    public static AddCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        Task blankTask = new Task(new TaskName("A"), TaskTime.getDefault(), TaskTime.getDefault(), TaskTime.getDefault(), TaskRecurrence.getDefault(), Tag.EMPTY_TAG);
        return new AddCommand(blankTask.update(fields));
    }
  //@@author

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
    
    @Override
    public boolean canUndo() {
        return true;
    }
}
