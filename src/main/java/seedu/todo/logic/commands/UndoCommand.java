package seedu.todo.logic.commands;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo the previous command";
    public static final String MESSAGE_NO_PREVIOUS_STATE = "There is no previous state to return to.";

    public UndoCommand() {}

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
