package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148145E
/**
 * Adds a task to the Task Scheduler.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the scheduler. "
            + "Parameters: TASK_NAME from START_DATE to END_DATE at LOCATION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Pretut\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Pretut by 8am 01-Oct-16\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Tutorial from today 8am to tomorrow 9am at NUS COM1-B103\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     */
    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            CommandHistory.setModTask(toAdd);
            CommandHistory.addExecutedCommand(this);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    @Override
    public CommandResult revert() {
        assert model != null;
        try {
            model.deleteTask(toAdd);
            CommandHistory.setModTask(null);
            CommandHistory.addRevertedCommand(this);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + toAdd));
    }

}
