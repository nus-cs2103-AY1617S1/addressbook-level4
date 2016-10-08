package taskle.model.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Model class to encapsulate the information provided
 * by each row in the help window.
 * @author Abel
 *
 */
public class CommandHelp {
    
    // Member variables for a CommandGuide object
    private String name;
    private String commandWord;
    private List<String> args; 
    
    /**
     * Private constructor for Command Guide so it cannot be constructed
     * without parameters
     */
    private CommandHelp() {
    }
    
    /**
     * Constructor for CommandGuide
     * @param name
     * @param commandWord
     * @param args
     */
    public CommandHelp(String name, String commandWord, String... args) {
        this.name = name;
        this.commandWord = commandWord;
        this.args = new ArrayList<>(
                Arrays.asList(args));
    }
}
