package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses the list command
 */
public class ListCommandParser extends CommandParser{
    public static final String COMMAND_WORD = ListTaskCommand.COMMAND_WORD;

    @Override
    public TaskCommand prepareCommand(String arguments) {
        
        return new ListTaskCommand();
    }

}
