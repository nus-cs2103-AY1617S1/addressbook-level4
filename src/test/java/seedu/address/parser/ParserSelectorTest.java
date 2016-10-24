package seedu.address.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.*;

public class ParserSelectorTest {

	@Test
	public void parserSelector_invalidCommand(){
		/* CommandResult should return a string that denotes that 
         * command is unknown.
         */
		CommandParser command = ParserSelector.getByCommandWord("invalid");
		CommandResult result = command.prepareCommand("").execute();
		String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(Messages.MESSAGE_UNKNOWN_COMMAND));
	}
	
	@Test
	public void parserSelector_add(){
		/* CommandParser should return a string that denotes that 
         * command is AddCommand.
         */
		AddCommandParser command = (AddCommandParser)ParserSelector.getByCommandWord("add");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("add"));
	}
	
	@Test
	public void parserSelector_delete(){
		/* CommandParser should return a string that denotes that 
         * command is DeleteCommand.
         */
		DeleteCommandParser command = (DeleteCommandParser)ParserSelector.getByCommandWord("delete");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("delete"));
	}
	
	@Test
	public void parserSelector_find(){
		/* CommandParser should return a string that denotes that 
         * command is FindCommand.
         */
		FindCommandParser command = (FindCommandParser)ParserSelector.getByCommandWord("find");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("find"));
	}
	
	@Test
	public void parserSelector_help(){
		/* CommandParser should return a string that denotes that 
         * command is HelpCommand.
         */
		HelpCommandParser command = (HelpCommandParser)ParserSelector.getByCommandWord("help");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("help"));
	}
	
	@Test
	public void parserSelector_list(){
		/* CommandParser should return a string that denotes that 
         * command is ListCommand.
         */
		ListCommandParser command = (ListCommandParser)ParserSelector.getByCommandWord("list");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("list"));
	}
	
	@Test
	public void parserSelector_update(){
		/* CommandParser should return a string that denotes that 
         * command is UpdateCommand.
         */
		UpdateCommandParser command = (UpdateCommandParser)ParserSelector.getByCommandWord("update");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("update"));
	}
	
	@Test
	public void parserSelector_favorite(){
		/* CommandParser should return a string that denotes that 
         * command is FavoriteCommand.
         */
		FavoriteCommandParser command = (FavoriteCommandParser)ParserSelector.getByCommandWord("favorite");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("favorite"));
	}
	
	@Test
	public void parserSelector_unfavorite(){
		/* CommandParser should return a string that denotes that 
         * command is UnfavoriteCommand.
         */
		UnfavoriteCommandParser command = (UnfavoriteCommandParser)ParserSelector.getByCommandWord("unfavorite");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("unfavorite"));
	}
	
	@Test
	public void parserSelector_complete(){
		/* CommandParser should return a string that denotes that 
         * command is CompleteCommand.
         */
		CompleteCommandParser command = (CompleteCommandParser)ParserSelector.getByCommandWord("complete");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("complete"));
	}
	
	@Test
	public void parserSelector_uncomplete(){
		/* CommandParser should return a string that denotes that 
         * command is UncompleteCommand.
         */
		UncompleteCommandParser command = (UncompleteCommandParser)ParserSelector.getByCommandWord("uncomplete");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("uncomplete"));
	}
	
	@Test
	public void parserSelector_alias(){
		/* CommandParser should return a string that denotes that 
         * command is AddAliasCommand.
         */
		AddAliasCommandParser command = (AddAliasCommandParser)ParserSelector.getByCommandWord("alias");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("alias"));
	}
	
	@Test
	public void parserSelector_unalias(){
		/* CommandParser should return a string that denotes that 
         * command is DeleteAliasCommand.
         */
		DeleteAliasCommandParser command = (DeleteAliasCommandParser)ParserSelector.getByCommandWord("unalias");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("unalias"));
	}
	
	@Test
	public void parserSelector_setStorage(){
		/* CommandParser should return a string that denotes that 
         * command is SetStorageCommand.
         */
		SetStorageCommandParser command = (SetStorageCommandParser)ParserSelector.getByCommandWord("setstorage");
		String feedback = command.COMMAND_WORD;
        assertTrue(feedback.equals("setstorage"));
	}
}
