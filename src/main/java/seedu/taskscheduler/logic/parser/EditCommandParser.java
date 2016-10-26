package seedu.taskscheduler.logic.parser;


import java.util.regex.Matcher;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.EditCommand;

//@@author A0148145E

/**
* Parses edit command user input.
*/
public class EditCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        
        final Matcher indexMatcher = INDEX_COMMAND_FORMAT.matcher(args);

        if (!indexMatcher.matches()) {
            return new EditCommand(-1,args);
        } else {
            int index = Integer.parseInt(indexMatcher.group("index"));
            assert index >= 0;
            String newArgs = indexMatcher.group("arguments").trim();
            return new EditCommand(index, newArgs);
        }
    }
}
