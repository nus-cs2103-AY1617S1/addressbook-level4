package seedu.emeraldo.logic.commands;


/**
* Edit the tags of a particular task in the task manager.
*/
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add/Delete/Clear the tags to/of the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: add/delete/clear INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "add" + " 1" + " #friends";
    
    
    
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
