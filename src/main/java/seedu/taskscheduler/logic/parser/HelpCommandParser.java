package seedu.taskscheduler.logic.parser;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.HelpCommand;

//@@author A0148145E

/**
* Parses help command user input.
*/
public class HelpCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new HelpCommand();
    }

    
}
