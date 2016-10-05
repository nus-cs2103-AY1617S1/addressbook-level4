package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.task.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a floating task to the task list. "
            + "Parameters: TASK i/INFORMATION t/LEVEL_OF_URGENCY_TAG \n"
            + "Example: " + COMMAND_WORD
            + " Chill i/do something you like t/1";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String task, String information, Set<String> levelOfUrgency)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : levelOfUrgency) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(task),
                new Info(information),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addTask(toAdd);
		return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

}
