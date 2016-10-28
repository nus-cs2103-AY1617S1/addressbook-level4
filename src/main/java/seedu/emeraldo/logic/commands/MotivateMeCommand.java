package seedu.emeraldo.logic.commands;

public class MotivateMeCommand extends Command {

    
    private static final String MESSAGE_MOTIVATE_ME_SUCCESS = null;

    public CommandResult execute() {

        
        Object motivateMe = null;
        return new CommandResult(String.format(MESSAGE_MOTIVATE_ME_SUCCESS, motivateMe));

    }
    
}
