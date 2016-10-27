package seedu.forgetmenot.logic.commands;

import seedu.forgetmenot.model.task.UniqueTaskList.TaskNotFoundException;

// @@author A0139198N
public class ClearDoneCommand extends Command {
    public static final String COMMAND_WORD = "cleardone";
    public static final String MESSAGE_SUCCESS = "Done tasks has been cleared!";
    public static final String NO_DONE_TASK = "No done task in the list.";
    public ClearDoneCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.saveToHistory();
        try {
			model.clearDone();
		} catch (TaskNotFoundException e) {
			return new CommandResult(NO_DONE_TASK);
		}
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
