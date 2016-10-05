package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "Add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a to-do task to Simply. "
            + "Parameters: Task details "
            + "Example: " + COMMAND_WORD
            + " go swimming.\n" + COMMAND_WORD + ": Adds a deadline task to Simply. "
            + "Parameters: Task details, date, end time "
            + "Example: " + COMMAND_WORD
            + " report, 120516, 1200.\n" + COMMAND_WORD + ": Adds a event task to Simply. "
            + "Parameters: [Task details, date, start time, end time] "
            + "Example:" + COMMAND_WORD
            + " [siloso beach party, 120716, 1600, 2200]";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in Simply";

    private final Person toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String phone, String email, String address, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Person(
                new Name(name),
                new Phone(phone),
                new Email(email),
                new Address(address),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
