package harmony.mastermind.logic.commands;

import java.util.HashSet;
import java.util.Set;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command implements Undoable{

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Mastermind. "
            + "Parameters: NAME at/TIME on/DATE [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " task at/1000 on/0110 t/finals";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Add Command] Task deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Mastermind";
    public static final String COMMAND_SUMMARY = "Adding a task:"
            + "\n" + COMMAND_WORD + " NAME at/TIME on/DATE [t/TAG]...";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String time, String date, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Task(
                new Name(name),
                new Time(time),
                new Date(date),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.getCommandHistory().push(this);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    @Override
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.deleteTask(toAdd);
            
            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd));
        } catch (UniqueTaskList.TaskNotFoundException pne) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }

}
