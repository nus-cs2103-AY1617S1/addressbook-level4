package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the FlexiTrack. "
            + "Parameters to add an event: [task title] from/ [starting time] to/ [ending time]"
            + "Example: " + COMMAND_WORD
            + " Summer school from/ 01062016 to/ 01/072016"
            + "Parameters to add a task: [task title] by/ [due date]"
            + "Example: " + COMMAND_WORD
            + " CS tutorial by/ 15102016";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String dueDate, String startTime, String endTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new DateTimeInfo(dueDate),
                new DateTimeInfo(startTime),
                new DateTimeInfo(endTime),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
