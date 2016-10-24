package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.UndoTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/**
 * Undoes the last command
 */
//@@author A0139817U
public class UndoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = UndoTaskCommand.COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
        return new UndoTaskCommand();
    }
}