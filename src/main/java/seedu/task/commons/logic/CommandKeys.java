//@@author A0144939R
package seedu.task.commons.logic;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents all commands in the TaskManager
 * @author advaypal
 *
 */
public class CommandKeys {
    
    public enum Commands {
        ADD("add"),
        ALIAS("alias"),
        CLEAR("clear"),
        COMPLETE("complete"),
        CHANGE_TO("change-to"),
        DELETE("delete"),
        EXIT("exit"),
        FIND("find"),
        HELP("help"),
        LIST("list"),
        PIN("pin"),
        SEARCH_BOX("searchbox"),
        SELECT("select"),
        UNDO("undo"),
        UPDATE("update"),
        UNCOMPLETE("uncomplete"),
        UNPIN("unpin");
        
        private String value;
        
        Commands(String command) {
            this.value = command;
        }
       
    }
    
    Set<String> commandWords = Arrays.stream(Commands.values()).map(Command -> Command.value).collect(Collectors.toSet());
    
    
}
