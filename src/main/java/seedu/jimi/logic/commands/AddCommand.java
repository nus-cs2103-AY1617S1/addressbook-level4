package seedu.jimi.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.*;

/**
 * Adds a task to the Jimi.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Adds a task to Jimi with one optional tag.\n"
            + "Parameters: \"TASK_DETAILS\" [by DATE_TIME] [t/TAG]\n"
            + "Example: " + COMMAND_WORD
            + " \"do dishes\" t/important";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Jimi";

    private final ReadOnlyTask toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String dateTime, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (dateTime == null) {
            this.toAdd = 
                    new FloatingTask(new Name(name), new UniqueTagList(tagSet));
        } else {
            this.toAdd = 
                    new DeadlineTask(new Name(name), new DateTime(dateTime), new UniqueTagList(tagSet));
        }
    }

    public AddCommand(String name, List<Date> dates, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (dates.size() == 0) {
            this.toAdd = 
                    new FloatingTask(new Name(name), new UniqueTagList(tagSet));
        } else {
            this.toAdd = 
                    new DeadlineTask(new Name(name), new DateTime(dates.get(0)), new UniqueTagList(tagSet));
        }

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
