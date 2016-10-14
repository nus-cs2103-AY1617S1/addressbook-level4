package seedu.emeraldo.logic.commands;

import seedu.emeraldo.commons.exceptions.IllegalValueException;

public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD            
            + ": Edits the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: INDEX (must be a positive integer) and \"TASK_DESCRIPTION\"\n"
            + "Example: " + COMMAND_WORD + " 1" + "CS2103T Week 8 Tutorial";
   
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    public final int targetIndex;
    
    public final String field;
    
    public EditCommand(String targetIndex, String field) throws IllegalValueException {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.field = field;
    }
    
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
