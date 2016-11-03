package seedu.emeraldo.logic.commands;

import static seedu.emeraldo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.emeraldo.commons.core.Messages;

/**
* Edit the tags of a particular task in the task manager.
*/
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add/Delete/Clear the tags to/of the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: add/delete/clear INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " add" + " 1" + " #friends";
    
    public static final String MESSAGE_TAG_EDIT_SUCCESS = "Edited task: %1$s";    
    
    private String action;
    private Set<String> tags;
    private int targetIndex;
    
    public TagCommand(String action, String targetIndex, Set<String> tags) {
        this.action = action;
        this.targetIndex = Integer.parseInt(targetIndex);
        this.tags = tags;
    }
    
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        
        if ((!action.equalsIgnoreCase("add"))||(!action.equalsIgnoreCase("delete"))||(!action.equalsIgnoreCase("clear"))){
            return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        
        return null;
    }

}
