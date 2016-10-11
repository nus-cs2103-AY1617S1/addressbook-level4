package seedu.taskman.logic.commands;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.event.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a Task to the task man.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    // todo, differed: let parameters be objects. we can easily generate the usage in that case
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to TaskMan. "
            + "Parameters: TITLE d/DEADLINE s/startDateTime, endDateTime f/frequency [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy bday present for dad d/next fri 1800 s/tdy 1800, tdy 2000 f/1 year t/Family";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This task already exists in TaskMan";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, String deadline, String schedule, String frequency, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Title(title),
                new UniqueTagList(tagSet),
                new Deadline(deadline),
                new Schedule(schedule),
                new Frequency(frequency)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueActivityList.DuplicateActivityException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        }

    }

}
