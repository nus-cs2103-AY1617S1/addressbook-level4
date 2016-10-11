package seedu.manager.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.manager.commons.exceptions.IllegalValueException;
import seedu.manager.model.activity.Activity;
import seedu.manager.model.tag.Tag;
import seedu.manager.model.tag.UniqueTagList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a activity to the activity manager.\n"
              + "\nUsage:\nadd ACTIVITY\n"
              + "add ACTIVITY on DATETIME"
              + "add ACTIVITY from DATETIME to DATETIME\n"
              + "\nExamples:\n"
              + "add buy bread"
              + "add complete assignment 0 on 25 Oct 1000"
              + "add attend conference from 23 Oct 1000 to 23 Oct 1200";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the address book";

    private final Activity toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name)
            throws IllegalValueException {
        this.toAdd = new Activity(name);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.name));
    }

}
