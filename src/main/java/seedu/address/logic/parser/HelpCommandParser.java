package seedu.address.logic.parser;


import seedu.address.logic.commands.taskcommands.HelpTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

public class HelpCommandParser extends CommandParser {
    public static final String COMMAND_WORD = HelpTaskCommand.COMMAND_WORD;
    
    /**
    * Parses arguments in the context of the find task command.
    *
    * @param args full command args string
    * @return the prepared command
    */

    @Override
    public TaskCommand prepareCommand(String arguments) {
        return new HelpTaskCommand();
    }

}
