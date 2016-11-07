package w15c2.tusk.logic.parser;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.AddAliasCommand;
import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.IncorrectCommand;

//@@author A0143107U
/*
 * Parses Alias commands
 */
public class AddAliasCommandParser extends CommandParser{
    public static final String COMMAND_WORD = AddAliasCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = AddAliasCommand.ALTERNATE_COMMAND_WORD;

    
	/**
     * Parses arguments in the context of the add alias command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public Command prepareCommand(String alias) {
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
            return new IncorrectCommand(ive.getMessage());
        }
	
	}
}
