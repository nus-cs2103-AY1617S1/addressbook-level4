package harmony.mastermind.logic.commands;

/**
 * Loads previous user input
 */
public class PreviousCommand extends Command {

    public static final String COMMAND_WORD = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"loads back your previous input.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully loaded previous input.";
    
    public PreviousCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}