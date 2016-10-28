package seedu.emeraldo.logic.commands;

public class MotivateMeCommand extends Command {

    
    public static final String MESSAGE_MOTIVATE_ME_SUCCESS = null;
    public static final String [] MESSAGE_MOTIVATE_LIST = {
            "Don't wish it was easier, wish you were better.", 
            "If not now, when?",
            "It does not matter how slowly you go as long as you do not stop.",
            "Time will pass, will you?",
            "When you feel like quitting, think about why you started."
    };
    public static final String COMMAND_WORD = "motivate me";

    public MotivateMeCommand() {}
    
    public CommandResult execute() {
        
        Object motivateMe = null;
        return new CommandResult(String.format(MESSAGE_MOTIVATE_ME_SUCCESS, motivateMe));

    }

    public static String [] getMessageMotivateList() {
        return MESSAGE_MOTIVATE_LIST;
    }
    
}
