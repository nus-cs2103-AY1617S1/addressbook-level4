package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    // @@author A0147944U
    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD_ALT = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK_NAME[, at <time>] #TAG...\n" + "TASK_NAME[, from <time> to <time>] #TAG...\n"
            + "TASK_NAME[, by <time>] #TAG...\n" + "Example: " + COMMAND_WORD
            + " do homework from 12.00pm to 01.00pm by 03.00pm #homework";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    // @@author

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime, String deadline, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        String recurring = "false";
        this.toAdd = new Task(new Name(name), new StartTime(startTime), new EndTime(endTime), new Deadline(deadline),
                new UniqueTagList(tagSet), new Status(false, false, false), new Recurring(recurring));
    }

    public AddCommand(String name, String startTime, String endTime, String deadline, Set<String> tags, Status status)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        String recurring = "false";
        this.toAdd = new Task(new Name(name), new StartTime(startTime), new EndTime(endTime), new Deadline(deadline),
                new UniqueTagList(tagSet), status, new Recurring(recurring));
    }

    // @@author A0147335E-reused
    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        try {
            model.addTask(toAdd);

            // @@author A0147944U
            // Sorts updated list of tasks
            model.autoSortBasedOnCurrentSortPreference();
            // @@author A0147335E-reused
            int currentIndex = model.getTaskManager().getTaskList().indexOf(toAdd);
            if (!isUndo) {
                getUndoList().add(new RollBackCommand(COMMAND_WORD, toAdd, null, currentIndex));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

    private ArrayList<RollBackCommand> getUndoList() {
        return history.getUndoList();
    }

    // insert a task at a specific index
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
