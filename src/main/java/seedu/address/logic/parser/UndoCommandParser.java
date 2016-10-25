package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.UndoTaskCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/**
 * Undoes the last command
 */
//@@author A0139817U
public class UndoCommandParser extends CommandParser{
    public static final String COMMAND_WORD = UndoTaskCommand.COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
    	if(!arguments.equals("")){
        	return new IncorrectTaskCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoTaskCommand.HELP_MESSAGE_USAGE));
        }
        return new UndoTaskCommand();
    }
}