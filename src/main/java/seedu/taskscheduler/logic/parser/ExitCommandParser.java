package seedu.taskscheduler.logic.parser;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.ExitCommand;

public class ExitCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ExitCommand();
    }
    

}
