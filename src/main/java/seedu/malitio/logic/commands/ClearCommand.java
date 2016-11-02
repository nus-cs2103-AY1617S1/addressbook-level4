package seedu.malitio.logic.commands;

import java.util.Arrays;

import seedu.malitio.model.Malitio;

//@@author a0126633j
/**
 * Clears the malitio partially or fully.
 */
public class ClearCommand extends Command {

    public static final String[] VALID_ARGUMENTS = {"", "expired" };            
    
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Malitio has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes multiple tasks at once.\n"
                                                + "Parameters: none OR  " + VALID_ARGUMENTS[1] +"\n"
                                                + "Example: clear" + VALID_ARGUMENTS[0]
                                                + ", clear " + VALID_ARGUMENTS[1];

    private String arg;
    
    public ClearCommand(String arg) {
        this.arg = arg;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.getFuture().clear();
        
        int index = Arrays.asList(VALID_ARGUMENTS).indexOf(arg);   
        switch (index) {
        case 0:
        model.resetData(Malitio.getEmptymalitio());
        break;
        
        case 1:
        model.clearExpiredTasks();
        break;
        
        default:
            assert(false); //impossible as check is done at parsing
        }
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
