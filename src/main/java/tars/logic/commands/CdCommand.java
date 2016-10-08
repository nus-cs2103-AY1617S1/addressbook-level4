/**
 * 
 */
package tars.logic.commands;

/**
 * @author Johnervan
 * Changes the directory of the Tars storage file, tars.xml 
 */
public class CdCommand extends Command {
    
    public static final String COMMAND_WORD = "cd";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the directory of the "
            + "TARS storage file, tars.xml";
    
    public static final String MESSAGE_SUCCESS = "Directory of tars.xml changed to: ";
    
    private final String filepath;
    
    public CdCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
