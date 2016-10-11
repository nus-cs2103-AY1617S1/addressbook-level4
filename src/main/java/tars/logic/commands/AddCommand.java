package tars.logic.commands;

import tars.commons.core.Messages;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.model.task.*;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.time.DateTimeException;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to tars.
 */
public class AddCommand extends UndoableCommand {

	public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to tars. "
            + "Parameters: NAME [-dt DATETIME] [-p PRIORITY] [-t TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " cs2103 project meeting -dt 05/09/2016 1400 to 06/09/2016 2200 -p h -t project";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK= "This task already exists in tars";
    public static final String MESSAGE_UNDO = "Deleted: %1$s";

    private final Task toAdd;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws DateTimeException if given dateTime string is invalid.
     */
    public AddCommand(String name, String[] dateTime, String priority, Set<String> tags)
            throws IllegalValueException, DateTimeException {

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toAdd = new Task(new Name(name), new DateTime(dateTime[0], dateTime[1]), new Priority(priority),
                new Status(), new UniqueTagList(tagSet));

    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    @Override
    public CommandResult undo() {
        try {
            model.deleteTask(toAdd);
            return new CommandResult(String.format(MESSAGE_UNDO, toAdd));
        } catch (TaskNotFoundException e) {
            return new CommandResult(Messages.MESSAGE_TASK_CANNOT_BE_FOUND);
        }
    }

}
