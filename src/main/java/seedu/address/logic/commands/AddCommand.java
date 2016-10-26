package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.event.*;
import seedu.address.model.activity.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the Lifekeeper.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Lifekeeper. "
            + "Parameters: NAME [d/DUEDATE] [p/PRIORITY] [r/REMINDER] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " Online Assignment\n"
            + COMMAND_WORD + " CS2103 T7A1 d/22-10-2016 12:00 p/1 r/21-01-2016 11:59 t/CS t/groupwork";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Lifekeeper";
    
    private final Activity toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
  //@@author A0131813R
    public AddCommand(String name, String duedate, String priority, String start, String end, String reminder,
            Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        String type = identifyActivityType(duedate, priority, start, end);

        if (type == "task") {

            this.toAdd = new Task(
                    new Name(name), 
                    new DueDate(duedate), 
                    new Priority(priority), 
                    new Reminder(reminder),
                    new UniqueTagList(tagSet));
        } else if (type == "event") {

            this.toAdd = new Event(new Name(name), new StartTime(start), new EndTime(start, end),
                    new Reminder(reminder), new UniqueTagList(tagSet));

        } else if (type == "float") {

            this.toAdd = new Activity(new Name(name), new Reminder(reminder), new UniqueTagList(tagSet));
        } else {
            assert false;
            throw new IllegalValueException(MESSAGE_INVALID_ACTIVITY_TYPE);
        }

    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);

            PreviousCommand addCommand = new PreviousCommand(COMMAND_WORD, toAdd);
            PreviousCommandsStack.push(addCommand);

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    
}
