//@@author A0141052Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

public class AddParser extends BaseParser {
    
    private final String FLAG_NAME = "";
    private final String FLAG_START_TIME = "s";
    private final String FLAG_CLOSE_TIME = "c";
    private final String FLAG_TAGS = "t";
    
    private final String[] KEYWORD_ARGS_REQUIRED = new String[]{FLAG_NAME};
    private final String[] KEYWORD_ARGS_OPTIONAL = new String[]{FLAG_START_TIME,
            FLAG_CLOSE_TIME,
            FLAG_TAGS
    };

    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        if (!checkForRequiredArguments(KEYWORD_ARGS_REQUIRED, KEYWORD_ARGS_OPTIONAL, true)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        try {
            return new AddCommand(getSingleKeywordArgValue(FLAG_NAME),
                    getSingleKeywordArgValue(FLAG_START_TIME),
                    getSingleKeywordArgValue(FLAG_CLOSE_TIME),
                    getTags());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private Set<String> getTags() {
        if (argumentsTable.containsKey(FLAG_TAGS)) {
            return new HashSet<>(argumentsTable.get(FLAG_TAGS));
        } else {
            return new HashSet<>();
        }
    }
}
