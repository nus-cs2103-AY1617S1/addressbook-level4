package seedu.taskscheduler.logic.parser;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.ListCommand;

public class ListCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ListCommand();
    }

    
}
