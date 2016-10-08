package taskle.model.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

/**
 * Model class to encapsulate the information provided
 * by each row in the help window.
 * @author Abel
 *
 */
public class CommandGuide {
    
    // Member variables for a CommandGuide object
    private String name;
    private String commandWord;
    private List<String> args; 
    
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
     * @param name
     * @param commandWord
     * @param args
     */
    public CommandGuide(String name, String commandWord, String... args) {
        assert name != null;
        assert commandWord != null;
        this.name = name;
        this.commandWord = commandWord;
        this.args = new ArrayList<>(
                Arrays.asList(args));
    }
    
    public String getName() {
        return name;
    }
    
    public String getCommandWord() {
        return commandWord;
    }
    
    public List<String> getArgs() {
        return args;
    }
}
