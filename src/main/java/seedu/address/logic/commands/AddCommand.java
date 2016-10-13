package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a to-do task to Simply. "
            + "Parameters: Task details "
            + "Example: " + COMMAND_WORD
            + " go swimming.\n" + COMMAND_WORD + ": Adds a deadline task to Simply. "
            + "Parameters: Task details, date, end time "
            + "Example: " + COMMAND_WORD
            + " report, 120516, 1200.\n" + COMMAND_WORD + ": Adds a event task to Simply. "
            + "Parameters: [Task details, date, start time, end time] "
            + "Example: " + COMMAND_WORD
            + " [siloso beach party, 120716, 1600, 2200]";

    public static final String EVENT_SUCCESS = "New event added: %1$s";
    public static final String DEADLINE_SUCCESS = "New deadline added: %1$s";
    public static final String TODO_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Simply";

    private final Task toAdd;
    private int taskCategory = 0;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String start, String end, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(date),
                new Start(start),
                new End(end),
                new UniqueTagList(tagSet)
        );
        taskCategory = 1;
    }   
    
    public AddCommand(String name, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(""),
                new Start(""),
                new End(""),
                new UniqueTagList(tagSet)
        );
        taskCategory = 2;
    }

    
    public AddCommand(String name, String date, String end, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(date),
                new Start(""),
                new End(end),
                new UniqueTagList(tagSet)
        );
        taskCategory = 3;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            if (taskCategory ==1) 
            	return new CommandResult(String.format(EVENT_SUCCESS, toAdd));
            else if (taskCategory ==2)
            	return new CommandResult(String.format(TODO_SUCCESS, toAdd));
            else
            	return new CommandResult(String.format(DEADLINE_SUCCESS, toAdd));

        } catch (UniqueTaskList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
