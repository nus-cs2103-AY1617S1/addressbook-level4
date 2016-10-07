package taskle.logic.commands;

/**
 * Edits a task identified using it's last displayed index from the task manager.
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the Task identified by the index number used in the last Task listing.\n"
            + "Format: edit task_number\n"
            + "Example: " + COMMAND_WORD + " 1 Buy dinner";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Removed Task: %1$s";

    public final int targetIndex;
    
    public final String newName;
    
    public EditCommand(int targetIndex, String newName) {
        this.targetIndex = targetIndex;
        this.newName = newName;
    }
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
