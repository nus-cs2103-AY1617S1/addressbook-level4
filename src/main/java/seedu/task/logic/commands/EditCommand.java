package seedu.task.logic.commands;

/**
 * Abstract class to represent generic edit operations for task and event.  
 * @author kian ming
 */

//@@author A0127570H
public abstract class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "Edits a task or event in the latest displayed list.\n\n"
            + "Editing a task.\n"
            + "Parameters: INDEX [NEW_NAME] [NEW_DESCRIPTION] [NEW_DEADLINE]\n"
            + "Example: "+ COMMAND_WORD + " /t 1 /desc Complete up to pg 24 of notes\n\n"
            + "Editing an event. \n"
            + "Parameters: LIST_TYPE [NEW_NAME] [NEW_DESCRIPTION] [NEW_START_DURATION] [NEW_END_DURATION]\n" 
            + "Example: "+ COMMAND_WORD + " /e 4 /from 7pm tomorrow /to 9pm";
    
    private static int targetIndex; //starts from index 1
    
    /**
     * Executes the command and returns the result message.
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();
    
    public static void setTargetIndex(int index) {
        targetIndex = index;
    }
    
    /**
     * @return targetIndex which has been offset by 1 for use
     */    
    public static int getTargetIndex() {
        return targetIndex - 1;
    }
}
