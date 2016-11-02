//@@author A0144939R
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.AliasCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

public class AliasParser extends BaseParser {
    
    private final String FLAG_ALIAS_COMMAND = "aliasCommand";
    private final String FLAG_ALIAS_VALUE ="aliasValue";
    private final int FLAG_ALIAS_COMMAND_LENGTH = 2;

    @Override
    public Command parse(String command, String arguments) {
        this.extractArguments(arguments);
        if(getSingleKeywordArgValue(FLAG_ALIAS_COMMAND) != null && getSingleKeywordArgValue(FLAG_ALIAS_VALUE) != null) {
            return new AliasCommand(getSingleKeywordArgValue(FLAG_ALIAS_COMMAND), getSingleKeywordArgValue(FLAG_ALIAS_VALUE));
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE)); 
        }

    }
    
    @Override
    protected void extractArguments(String args) {
        argumentsTable.clear();
        String[] segments = args.trim().split(" ");
        if(segments.length == FLAG_ALIAS_COMMAND_LENGTH) {
            addToArgumentsTable(FLAG_ALIAS_COMMAND, segments[0]);
            addToArgumentsTable(FLAG_ALIAS_VALUE, segments[1]);
        }
        //do nothing if false. The parse method will detect if there's an error.
    }
    
}
