package seedu.taskscheduler.logic.parser;

import seedu.taskscheduler.logic.commands.ClearCommand;
import seedu.taskscheduler.logic.commands.Command;

public class ClearCommandParser extends CommandParser{

    @Override
    public Command prepareCommand(String args) {
        return new ClearCommand();
    }
}
