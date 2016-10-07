package taskle.logic.commands;

import taskle.commons.exceptions.IllegalValueException;
import taskle.model.person.FloatTask;
import taskle.model.person.Name;
import taskle.model.person.Task;
import taskle.model.person.UniqueTaskList;
import taskle.model.tag.UniqueTagList;

/**
 * Adds a task to the Task Manager.
 */
public class AddCommand extends Command {
    /** 
     * stub unique tag list used for every add commands for now
     */
    UniqueTagList stubTagList = new UniqueTagList();

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Task Manager."
            + "Format: add task_name [by date time] [remind date time]"
            + " or add task_name [from date time] [to date time] [remind date time]"
            + "Example: " + COMMAND_WORD
            + " add Business Trip from 4 Oct to 5 Oct remind 3 Oct 2pm";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Task Manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name)
            throws IllegalValueException {
        this.toAdd = new FloatTask(new Name(name), stubTagList);
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

