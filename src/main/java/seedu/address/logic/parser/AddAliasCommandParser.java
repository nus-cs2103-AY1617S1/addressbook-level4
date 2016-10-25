package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddAliasCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Parses Alias commands
 */
//@@author A0143107U
public class AddAliasCommandParser extends CommandParser{
    public static final String COMMAND_WORD = AddAliasCommand.COMMAND_WORD;
    
	/**
     * Parses arguments in the context of the add alias command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String alias) {
		String shortcut = null;
		String sentence = null;
		if(!alias.isEmpty()){
			int space = alias.indexOf(" ");
			if(space == -1){
				shortcut = alias;
			}
			else{
				shortcut = alias.substring(0, space);
				sentence = alias.substring(space + 1);
			}
		}
		
		try {
			return new AddAliasCommand(shortcut, sentence);
		} catch (IllegalValueException ive) {
            return new IncorrectTaskCommand(ive.getMessage());
        }
	
	}
}
