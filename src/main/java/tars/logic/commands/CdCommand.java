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
    
    public final String MESSAGE_SUCCESS = "Directory of tars.xml changed to: " + this.toString();
    
    private final String filePath;
    
    public CdCommand(String filepath) {
        this.filePath = filepath;
    }
    
    @Override
    public String toString() {
        return this.filePath;
    }

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
