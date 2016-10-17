/**
 * Original @@author SAN SOK SAN A0140037W
 */
package seedu.address.logic.commands;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redid the most recent undone comment.";
    public static final String MESSAGE_NO_MORE_ACTION = "No further action to redo.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": redo the undo action.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
    
        if(!model.restoreTaskForce()){
            return new CommandResult(MESSAGE_NO_MORE_ACTION);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
