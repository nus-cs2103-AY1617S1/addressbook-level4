package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.ListTaskCommand;
import seedu.address.logic.parser.ListCommandParser;

public class ListCommandParserTest {
	// Initialized to support the tests
		ListCommandParser parser = new ListCommandParser();
		
		/**
		 * Testing correct handling of invalid formats, list types
		 * 
		 */
		@Test
		public void prepareCommand_invalidFormat() {
			String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE);
			
			IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("meeting");
			String feedback = command.feedbackToUser;
			assertEquals(feedback, expected);
			
			command = (IncorrectTaskCommand) parser.prepareCommand("all");
			feedback = command.feedbackToUser;
			assertEquals(feedback, expected);
		}
		/**
		 * Testing correct list alias type
		 */
		@Test
		public void prepareCommand_listAlias() {
			String expected = "alias";
			
			ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("alias");
			String feedback = command.getType();
			assertEquals(feedback, expected);
			
		}
		
		/**
		 * Testing correct list completed type
		 */
		@Test
		public void prepareCommand_listCompleted() {
			String expected = "completed";
			
			ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("completed");
			String feedback = command.getType();
			assertEquals(feedback, expected);
			
			expected = "complete";
			
			command = (ListTaskCommand) parser.prepareCommand("complete");
			feedback = command.getType();
			assertEquals(feedback, expected);
			
		}
		
		/**
		 * Testing correct list all type
		 */
		@Test
		public void prepareCommand_listAll() {
			String expected = "";
			
			ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("");
			String feedback = command.getType();
			assertEquals(feedback, expected);
			
		}
}
