package taskle.model.help;

/**
 * Model class to encapsulate the information provided
 * by each row in the help window.
 * @author Abel
 *
 */
public class CommandGuide {
    
    // Member variables for a CommandGuide object
    private String actionName;
    private String commandWord;
    private String[] args; 
    
    /**
     * Private constructor for Command Guide so it cannot be constructed
     * without parameters
     */
    private CommandGuide() {
    }
    
    /**
     * Constructor for CommandGuide
     * Asserts that name and commandWord are non-null because it is
     * constructed by custom parameters in HelpWindow always.
     * @param name Name of the action and command
     * @param commandWord command keyword
     * @param args optional arguments for command keyword
     */
    public CommandGuide(String name, String commandWord, String... args) {
        assert name != null;
        assert commandWord != null;
        this.actionName = name;
        this.commandWord = commandWord;
        this.args = args;
    }
    
    public String getName() {
        return actionName;
    }
    
    public String getCommandWord() {
        return commandWord;
    }
    
    public String[] getArgs() {
        return args;
    }
}
