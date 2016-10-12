package seedu.jimi.logic.commands;

/**
 *  
 * @author Wei Yin 
 * 
 * Sets save directory for the tasks and events in Jimi.
 */
public class SaveAsCommand extends Command {

    public static final String COMMAND_WORD = "saveas";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a new save directory for all your tasks and events in Jimi.\n"
            + "Parameters: FILEPATH/FILENAME.xml \n"
            + "Example: " + COMMAND_WORD
            + " saveas C:/dropbox/taskbook.xml";

    public static final String MESSAGE_SUCCESS = "Save directory changed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This save directory is originally used in Jimi";

    private String taskBookFilePath;

    /**
     * Convenience constructor using raw values.
     */
    public SaveAsCommand(String filePath)  {
        this.taskBookFilePath = filePath;
    }
    
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
