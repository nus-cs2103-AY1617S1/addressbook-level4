package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME" 
            + " Example: " + COMMAND_WORD
            + " CS2103T Software Engineeringv0.1";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws ParseException 
     */
    public AddCommand(String name, String startline, Set<String> deadlines, String priority, Set<String> tags)
            throws IllegalValueException {
    	final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        final Set<Deadline> deadlineSet = new HashSet<>();
        for (String deadlineDate : deadlines) {
            deadlineSet.add(new Deadline(deadlineDate));
        }
        
        this.toAdd = new Task(
                new Name(name),
                new Startline(startline),
                new UniqueDeadlineList(deadlineSet),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
