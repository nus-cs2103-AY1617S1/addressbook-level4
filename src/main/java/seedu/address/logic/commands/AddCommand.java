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

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the to-do-list. "
            + "Parameters: TASKNAME p/TIME e/EMAIL a/DATE  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Work on school project. p/from 5pm to 7pm e/ a/10/10/16 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the to-do-list";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name) //, String phone, String email, String address, Set<String> tags)
            throws IllegalValueException {
//        final Set<Tag> tagSet = new HashSet<>();
//        for (String tagName : tags) {
//            tagSet.add(new Tag(tagName));
//        }
        this.toAdd = new Task(
                new Name(name));
//                new Phone(phone),
//                new Email(email),
//                new Address(address),
//                new UniqueTagList(tagSet)
//        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
