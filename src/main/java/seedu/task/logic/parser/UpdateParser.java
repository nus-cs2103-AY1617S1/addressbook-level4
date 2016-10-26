//@@author A0141052Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UpdateCommand;

public class UpdateParser extends BaseParser {
    private final String FLAG_NAME = "";
    private final String FLAG_START_TIME = "s";
    private final String FLAG_CLOSE_TIME = "c";
    private final String FLAG_TAGS = "t";
    private final String FLAG_REMOVE_TAGS = "rt";
    
    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        
        String[] nameIdPair = extractNameAndId(getSingleKeywordArgValue(FLAG_NAME));
        String name = "";
        
        if (nameIdPair.length < 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        } else if (nameIdPair.length > 1) {
            name = nameIdPair[1];
        }
        
        Optional<Integer> possibleIndex = parseIndex(nameIdPair[0]);
        
        if (!possibleIndex.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }
        
        try {
            return new UpdateCommand(
                    possibleIndex.get(),
                    name,
                    getSingleKeywordArgValue(FLAG_START_TIME),
                    getSingleKeywordArgValue(FLAG_CLOSE_TIME),
                    getTags(FLAG_TAGS),
                    getTags(FLAG_REMOVE_TAGS)
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private Set<String> getTags(String keyword) {
        if (argumentsTable.containsKey(keyword)) {
            return new HashSet<>(argumentsTable.get(keyword));
        } else {
            return new HashSet<>();
        }
    }
    
    private String[] extractNameAndId(String combinedArgument) {
        return combinedArgument.split("\\s", 2);
    }
}