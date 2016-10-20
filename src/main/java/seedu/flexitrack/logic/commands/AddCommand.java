package seedu.flexitrack.logic.commands;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.tag.Tag;
import seedu.flexitrack.model.tag.UniqueTagList;
import seedu.flexitrack.model.task.*;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the FlexiTrack.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the FlexiTrack.\n"
            + "1. Add Floating Task - Parameters to add an Floating Task: [task title]\n"
            + "\tExample: " + COMMAND_WORD + " Do CS homework\n"
            + "2. Add Event - Parameters to add an event: [task title] from/ [starting time] to/ [ending time]\n"
            + "\tExample: " + COMMAND_WORD
            + " Summer school from/ 01062016 to/ 01/072016\n"
            + "3. Add Task - Parameters to add a task: [task title] by/ [due date]\n"
            + "\tExample: " + COMMAND_WORD
            + " CS tutorial by/ 15102016\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the FlexiTrack";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String dueDate, String startTime, String endTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new DateTimeInfo(dueDate),
                new DateTimeInfo(startTime),
                new DateTimeInfo(endTime),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            if (toAdd.getIsEvent()){ 
                return new CommandResult((String.format(MESSAGE_SUCCESS, toAdd)) + "\n" +
                        DateTimeInfo.durationOfTheEvent(toAdd.getStartTime().toString(), toAdd.getEndTime().toString()));
            } else { 
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
            }
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
