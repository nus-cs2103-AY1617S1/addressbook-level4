package seedu.emeraldo.logic.commands;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
import seedu.emeraldo.model.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    
    //@@author A0139749L
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: \"TASK_DESCRIPTION\" [on DATE] [by DATE_TIME] [from START_DATE_TIME]"
            + "[to END_DATE_TIME] [#TAGS]...\n"
            + "Example: " + COMMAND_WORD
            + " \"CS2103T Lecture\" on 7 Oct 2016 from 2pm to 4pm #Important";
    //@@author
    
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String dateTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Description(description),
                new DateTime(dateTime.trim()),
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
