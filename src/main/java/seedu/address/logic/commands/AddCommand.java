package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a person to the address book. "
            + "Task details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: NAME [p]s/START [p]e/END [p]p/PRIORITY [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " Sleep s/8 Oct e/9 Oct p/high";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the Task Manager.";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
//              new StartDate(startDate),
                new StartTime(startTime),
//              new EndDate(endDate),
                new EndTime(endTime),
                new UniqueTagList(tagSet)
        );
    }
    
    /**
     * Constructor for floating tasks.
     * 
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Set<String> tags) throws IllegalValueException {
        this(name, "NIL", "NIL", tags);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
