//@@author A0144939R
package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME s/start-datetime c/closedatetime [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Finish CS2103";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Added task removed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String openTime, String closeTime, Set<String> tags) 
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new DateTime(openTime),
                new DateTime(closeTime),
                false,
                false,
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(true, String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(false, MESSAGE_DUPLICATE_TASK);
        }
    }
//@author    
    @Override
    public CommandResult rollback() {
        assert model != null;
        model.rollback();
        
        return new CommandResult(true, String.format(MESSAGE_ROLLBACK_SUCCESS, toAdd));
    }

}
