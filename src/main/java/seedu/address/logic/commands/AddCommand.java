package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Datetime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Status;
import seedu.address.model.task.Status.State;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book.\n"
            + "Parameters: TASKNAME d/TASK_DESCRIPTION date/DD-MM-YYYY [24HR] [to 24HR] [t/TAG]...\n" + "Example: "
            + COMMAND_WORD + " Wash Clothes d/Wash with detergent date/27-9-2016 2359 t/!!!";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String phone, String datetime, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(new Name(name), 
                new Description(phone), 
                new Datetime(datetime), 
                new Status(State.NONE),
                new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            if (isMutating()){
                model.addUndo(COMMAND_WORD, toAdd);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean isMutating() {
        return true;
    }
}
