package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 * @@author A0147335E reused
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK_NAME, from <time> to <time> by <time> #TAG...\n"
            + "Example: " + COMMAND_WORD
            + " do homework from 12.00pm to 01.00pm by 03.00pm #homework";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime, String deadline, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new StartTime(startTime), new EndTime(endTime), new Deadline(deadline), new UniqueTagList(tagSet), new Status(false, false, true));
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        try {
            model.addTask(toAdd);
            if(isUndo == false){
                history.getUndoList().add(new RollBackCommand(COMMAND_WORD, toAdd, null));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    public CommandResult execute(int index) {
        assert model != null;
        try {
            model.addTask(index, toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
