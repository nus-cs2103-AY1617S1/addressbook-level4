package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the scheduler. "
            + "Parameters: TASK s/START_DATE e/END_DATE at LOCATION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Pretut\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Pretut by 011016\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Tutorial s/011016 e/011016 at NUS COM1-B103\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String endDate, String address)
            throws IllegalValueException {
        
        this.toAdd = new Task(
                new Name(name),
                new TaskDateTime(startDate),
                new TaskDateTime(endDate),
                new Location(address),
                new UniqueTagList()
        );
    }
    public AddCommand(String name, String startDate, String endDate, String address, String... tags)
            throws IllegalValueException {
        
        this(name,startDate,endDate,address);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd.setTags(new UniqueTagList(tagSet));
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
