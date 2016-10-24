package seedu.taskscheduler.logic.parser;

import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.UndoCommand;

//@@author A0140007B

/**
* Parses setpath command user input.
*/
public class UndoCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new UndoCommand();
    }

}
