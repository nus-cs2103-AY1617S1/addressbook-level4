package seedu.todo.logic.commands;
//@@author A0121643R
/**
 * Undoes the user's last action
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undoes the previous command.";
    public static final String MESSAGE_NO_PREVIOUS_STATE = "There is no previous state to return to.";

    @Override
    public CommandResult execute() {
        if (model.undo()) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_NO_PREVIOUS_STATE);
        }

    }
}
