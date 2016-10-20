package seedu.task.logic.commands;

/**
 * Deletes an item identified using it's last displayed index from the address book.
 * @author Tiankai
 * */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
    		+ "Deletes an existing task/event from the TaskBook storage completely.\n\n"
            + "Deletes a task at the specified INDEX in the most recent task listing.\n"
            + "Parameters: DELETE_TYPE + INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -t" + " 1\n\n"
            + "Deletes a event at the specified INDEX in the most recent event listing.\n"
            + "Parameters: DELETE_TYPE + INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -e" + " 1";

    public int targetIndex;


    @Override
    public abstract CommandResult execute();

}
