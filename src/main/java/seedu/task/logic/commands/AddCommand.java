package seedu.task.logic.commands;


//@@author A0127570H
/**
 * Abstract class to represent generic add operations.  
 * @author kian ming
 */

public abstract class AddCommand extends UndoableCommand {
    
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
    		+ "Adds a task or event to the task book.\n"
            + "The order of the parameters is flexible\n\n"
    		+ "Adding a task.\n"
            + "Parameters: TASK_NAME [/desc DESCRIPTION] [/by DEADLINE]\n"
    		+ "DEADLINE can be in words or MM-DD-YY\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Lab 6 /desc hand in through codecrunch /by tomorrow\n\n"
            + "Adding an event.\n"
            + "Parameters: EVENT_NAME [/desc DESCRIPTION] [/from START_DURATION] [/to END_DURATION]\n"
            + "DURATION can be in words or MM-DD-YY\n"
            + "Entering either one or both durations are accepted.\n"
            + "Duration is set to 1 hour at default if only one duration is entered.\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Workshop /desc OOP workshop /from tomorrow /to thursday\n"
            + "DURATION: Event is assumed to terminate on the same day if a single date is entered";
    
    /**
     * Executes the command and returns the result message.
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();
}
