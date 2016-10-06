package harmony.logic.commands;

import java.util.HashSet;
import java.util.Set;

import harmony.commons.core.Messages;
import harmony.commons.exceptions.IllegalValueException;
import harmony.model.tag.Tag;
import harmony.model.tag.UniqueTagList;
import harmony.model.task.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command implements Undoable{

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: NAME p/PHONE e/EMAIL [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Add Command] Person deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String phone, String email, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Time(phone),
                new Date(email),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addPerson(toAdd);
            model.getCommandHistory().push(this);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }
    
    @Override
    public CommandResult undo() {
        try {
            // remove the person that's previously added.
            model.deletePerson(toAdd);
            
            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.PersonNotFoundException pne) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

}
