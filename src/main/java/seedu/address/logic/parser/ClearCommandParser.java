package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.ClearTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses Clear commands
 */
//@@author A0139817U
public class ClearCommandParser extends CommandParser{
	public static final String COMMAND_WORD = ClearTaskCommand.COMMAND_WORD;
	
	 /**
     * Parses arguments in the context of the clear task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    public TaskCommand prepareCommand(String arguments) {
        return new ClearTaskCommand();
    }
}
