package tars.logic.commands;

import tars.commons.exceptions.IllegalValueException;
import tars.model.task.*;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to tars.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to tars. "
            + "Parameters: NAME -dt DATETIME -p PRIORITY [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " cs2103 project meeting -dt 5/9/2016 1400 to 5/9/2015 2200 -p h -t project";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK= "This task already exists in tars";
    
    private static final int DATETIME_INDEX_OF_ENDDATE = 1;
	private static final int DATETIME_INDEX_OF_STARTDATE = 0;
	private static final int DATETIME_INDEX_OF_DEADLINE = 0;

    private final Task toAdd;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String[] dateTime, String priority, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (dateTime.length == 1) {
            this.toAdd = new Task(
                    new Name(name),
                    new DateTime(null, dateTime[DATETIME_INDEX_OF_DEADLINE]),
                    new Priority(priority),
                    new Status(),
                    new UniqueTagList(tagSet)
            );
        } else {
            this.toAdd = new Task(
                    new Name(name),
                    new DateTime(dateTime[DATETIME_INDEX_OF_STARTDATE], dateTime[DATETIME_INDEX_OF_ENDDATE]),
                    new Priority(priority),
                    new Status(),
                    new UniqueTagList(tagSet)
            );
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
