package tars.logic.commands;

import java.util.Set;

/**
 * Confirms a specified datetime for a reserved task and add it into the task list
 * 
 * @@author A0124333U
 */

public class ConfirmCommand extends UndoableCommand {
    
    public static final String COMMAND_WORD = "confirm";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms a datetime for a reserved task"
            + " and adds the task into the task list.\n"
            + "Parameters: RESERVED_TASK_INDEX DATETIME_INDEX \n" 
            + "Example: " + COMMAND_WORD + " 1 3";
    
    public static final String MESSAGE_CONFIRM_SUCCESS = "Task Confirmation Success!";
    
    private final int taskIndex;
    private final int dateTimeIndex;
    
    public ConfirmCommand(int taskIndex, int dateTimeIndex, String priority, Set<String> tags) {
        this.taskIndex = taskIndex;
        this.dateTimeIndex = dateTimeIndex;
    }
    
    

    @Override
    public CommandResult undo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult redo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult execute() {
        
        return null;
    }

}
