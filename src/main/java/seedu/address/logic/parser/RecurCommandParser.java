package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.RecurCommand;

public class RecurCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        
        args = args.trim();
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    RecurCommand.MESSAGE_USAGE));
        }
        Matcher matcher = INDEX_COMMAND_FORMAT.matcher(args);
        if(matcher.matches()){
            return new RecurCommand(Integer.parseInt(matcher.group("index")),
                    matcher.group("arguments"));
        } else {
            return new RecurCommand(args);
        }
    }
}
