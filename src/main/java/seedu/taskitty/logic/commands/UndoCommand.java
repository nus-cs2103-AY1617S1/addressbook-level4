package seedu.taskitty.logic.commands;

public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undos the previous action\n"
            + "Example: " + COMMAND_WORD;

    private static final String MESSAGE_UNDO_TASK_SUCCESS = "Undoed previous action";
    
    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }

}
