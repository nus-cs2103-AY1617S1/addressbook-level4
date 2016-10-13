package seedu.address.logic.commands;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by index as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example 1: " + COMMAND_WORD + " 1\n";

    public static final String MESSAGE_DONE_ITEM_SUCCESS = "Task marked as complete!";
    public static final String MESSAGE_ITEM_NOT_FOUND = "Task ID not found.";

    public final int targetIndex;

    public DoneCommand(int index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }


}
