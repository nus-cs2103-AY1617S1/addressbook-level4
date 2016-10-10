package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event/ deadline/ task to be done someday to the task manager.\n"
            + "Event Parameters: event 'NAME' from hh:mm to hh:mm on dd-mm-yy\n"
            + "Event Example: " + COMMAND_WORD
            + " event 'dinner with wife' from 19:00 to 21:00 on 25-12-16\n"
            + "Deadline Parameters: deadline 'NAME' by hh:mm dd-mm-yy\n"
            + "Deadline Example: " + COMMAND_WORD
            + " deadline 'lab report' by 16:00 03-03-15\n"
            + "Task to Be Done Someday Parameters: someday 'NAME'\n"
            + "Task to Be Done Someday Example: " + COMMAND_WORD
            + " someday 'read EL James' book 50 Shades of Grey'";
    
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

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
        this.toAdd = new Task(
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
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
