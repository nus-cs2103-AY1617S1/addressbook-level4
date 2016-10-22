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
			String expectedTask = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE);
			
			IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("meeting");
			String actualTask = command.feedbackToUser;
			assertEquals(actualTask, expectedTask);
			
			command = (IncorrectTaskCommand) parser.prepareCommand("all");
			actualTask = command.feedbackToUser;
			assertEquals(actualTask, expectedTask);
		}
		/**
		 * Testing correct list alias type
		 */
		@Test
		public void prepareCommand_listAlias() {
			String expectedTask = "alias";
			
			ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("alias");
			String actualTask = command.getType();
			assertEquals(actualTask, expectedTask);
			
		}
		
		/**
		 * Testing correct list completed type
		 */
		@Test
		public void prepareCommand_listCompleted() {
			String expectedTask = "completed";
			
			ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("completed");
			String actualTask = command.getType();
			assertEquals(actualTask, expectedTask);
			
			expectedTask = "complete";
			
			command = (ListTaskCommand) parser.prepareCommand("complete");
			actualTask = command.getType();
			assertEquals(actualTask, expectedTask);
			
		}
		
		/**
		 * Testing correct list all type
		 */
		@Test
		public void prepareCommand_listAll() {
			String expectedTask = "";
			
			ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("");
			String actualTask = command.getType();
			assertEquals(actualTask, expectedTask);
			
		}
}
