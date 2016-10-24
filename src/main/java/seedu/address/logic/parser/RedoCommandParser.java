package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.RedoTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/**
 * Redoes the last command
 */
//@@author A0139817U
public class RedoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = RedoTaskCommand.COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
        return new RedoTaskCommand();
    }
}