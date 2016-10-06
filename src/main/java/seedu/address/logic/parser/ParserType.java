package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;

public abstract class ParserType {
	public static CommandParser get(String commandWord){
		 switch (commandWord) {
	        case AddTaskCommand.COMMAND_WORD:
	            return new AddCommandParser();

	        case DeleteTaskCommand.COMMAND_WORD:
	            return new DeleteCommandParser();

	        default:
	            return new IncorrectCommandParser();
	        }
	}
}
