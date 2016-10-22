package seedu.todo.logic;

import com.google.common.base.Joiner;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.commands.CommandMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//@@author A0135817B
/**
 * Selects the correct command based on the parser results
 */
public class TodoDispatcher implements Dispatcher {
    private final static String COMMAND_NOT_FOUND_FORMAT = "'%s' doesn't look like any command we know.";
    private final static String AMBIGUOUS_COMMAND_FORMAT = "Do you mean %s?";
    
    public BaseCommand dispatch(String input) throws IllegalValueException {
        // Implements character by character matching of input to the list of command names
        // Since this eliminates non-matches at every character, it is fast even though 
        // it is theoretically O(n^2) in the worst case.
        Set<String> commands = new HashSet<>(CommandMap.getCommandMap().keySet());
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            Iterator<String> s = commands.iterator();
            while (s.hasNext()) {
                String commandName = s.next();
                if (input.length() > commandName.length() || commandName.charAt(i) != c) {
                    s.remove();
                }
            }
            
            // Return immediately when there's one match left. This allow the user to 
            // type as little as possible, and is also good for autocomplete if that's 
            // on the radar
            if (commands.size() == 1) {
                String key = commands.iterator().next();
                return CommandMap.getCommand(key);
            } else if (commands.isEmpty()) {
                throw new IllegalValueException(String.format(TodoDispatcher.COMMAND_NOT_FOUND_FORMAT, input));
            }
        }
        
        String ambiguousCommands = Joiner.on(" or ").join(commands);
        throw new IllegalValueException(String.format(TodoDispatcher.AMBIGUOUS_COMMAND_FORMAT, ambiguousCommands));
    }
}
