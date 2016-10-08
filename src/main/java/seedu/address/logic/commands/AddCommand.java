package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task, deadline or event to the task manager. \n"
            + "Task Parameters: " + Type.TASK_WORD + " n/NAME \n"
            + "Deadline Parameters: " + Type.DEADLINE_WORD + " n/NAME d/[DATE] t/[TIME] \n"
            + "Event Parameters: " + Type.EVENT_WORD + " n/NAME sd/[DATE] st/[TIME] ed/[DATE] et/[TIME] \n"
            + "Example (Task): " + COMMAND_WORD +  " " + Type.TASK_WORD 
            + " Win Facebook hackathon";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Item toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String itemType, String name, String email, String address, Set<String> tags)
            throws IllegalValueException {
        if (email == null) {
            email = "PLACE@HOLDER";
        }
        if (address == null) {
            address = "PLACEHOLDER";
        }
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Item(
                new ItemType(itemType),
                new Name(name),
                new Date(email),
                new Time(address),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addItem(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
