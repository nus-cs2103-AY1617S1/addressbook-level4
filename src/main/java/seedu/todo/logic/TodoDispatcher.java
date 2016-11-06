package seedu.todo.logic;

import com.google.common.base.Joiner;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.commands.CommandMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//@@author A0135817B
/**
 * Selects the correct command based on the parser results
 */
public class TodoDispatcher implements Dispatcher {
    private final static String COMMAND_NOT_FOUND_FORMAT = "'%s' doesn't look like any command we know.";
    private final static String AMBIGUOUS_COMMAND_FORMAT = "Do you mean %s?";
    
    public BaseCommand dispatch(String input) throws IllegalValueException {

        List<String> commands = CommandMap.filterCommandKeys(input);

        // Return immediately when there's one match left. This allow the user to
        // type as little as possible
        if (commands.size() == 1) {
            String key = commands.iterator().next();
            return CommandMap.getCommand(key);
        } else if (commands.isEmpty()) {
            throw new IllegalValueException(String.format(TodoDispatcher.COMMAND_NOT_FOUND_FORMAT, input));
        }

        String ambiguousCommands = Joiner.on(" or ").join(commands);
        throw new IllegalValueException(String.format(TodoDispatcher.AMBIGUOUS_COMMAND_FORMAT, ambiguousCommands));
    }
}
