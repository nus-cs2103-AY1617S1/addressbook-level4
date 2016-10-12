package seedu.inbx0.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.*;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;

/**
 * Adds a task to the tasklist.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the tasklist. \n"
            + "1. Event Task \n"
            + "Parameters: NAME s/STARTDATE st/STARTTIME e/ENDDATE et/ENDTIME [i/IMPORTANCE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " homework s/tomorrow st/9a e/next week et/9a i/r t/CS2103 t/project \n \n"
            + "2. Floating Task \n"
            + "Parameters: NAME [i/IMPORTANCE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " read shakespeare i/r t/Hamlet t/Romeo and Juliet \n \n"
            + "3. Deadline Task \n"
            + "Parameters: NAME e/ENDDATE et/ENDTIME [i/IMPORTANCE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " complete project e/next month et/10a i/r t/Hamlet t/Romeo and Juliet \n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the tasklist";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String startTime, String endDate, String endTime, String level, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(startDate),
                new Time(startTime),
                new Date(endDate),
                new Time(endTime),
                new Importance(level),
                new UniqueTagList(tagSet)
        );
    }
    
    public AddCommand(String name, String startDate, String startTime, String endDate, String endTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(startDate),
                new Time(startTime),
                new Date(endDate),
                new Time(endTime),
                new Importance(""),
                new UniqueTagList(tagSet)
        );
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
                new Time(""),
                new Date(""),
                new Time(""),
                new Importance(""),
                new UniqueTagList(tagSet)
        );
    }
    
    public AddCommand(String name, String level, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(""),
                new Time(""),
                new Date(""),
                new Time(""),
                new Importance(level),
                new UniqueTagList(tagSet)
        );
    }
    
    public AddCommand(String name, String endDate, String endTime, String level, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(""),
                new Time(""),
                new Date(endDate),
                new Time(endTime),
                new Importance(level),
                new UniqueTagList(tagSet)
        );
    }
    
    public AddCommand(String name, String endDate, String endTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(""),
                new Time(""),
                new Date(endDate),
                new Time(endTime),
                new Importance(""),
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
