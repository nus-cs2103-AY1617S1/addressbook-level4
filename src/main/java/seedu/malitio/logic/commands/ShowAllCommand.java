package seedu.malitio.logic.commands;

/**
 * Lists all or specified tasks in Malitio to the user.
 * @@author A0122460W
 */
public class ShowAllCommand extends Command {

    public static final String COMMAND_WORD = "showall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all task in Malitio\n" +
            "Parameters: listall\n" +
            "Example: " + COMMAND_WORD;

    public static final String ALL_MESSAGE_SUCCESS = "Listed all tasks from beginning of time";

    public ShowAllCommand() {}


    @Override
    public CommandResult execute() {
    	model.ShowAllTask();
    	return new CommandResult(ALL_MESSAGE_SUCCESS);
        
    }
}
