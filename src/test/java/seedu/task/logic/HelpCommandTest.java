package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskcommons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.MarkCommand;

public class HelpCommandTest extends CommandTest {
	/******************************
	 * Pre and Post set up
	 *****************************/
	@Before
	public void setup() {
		super.setup();
	}

	@After
	public void teardown() {
		super.teardown();
	}

	/************************************
	 * Test cases
	 *****************************/

	/*
	 * Tests for executing help command
	 */

	/*
	 * 1) Invalid help parameters 
	 * - Null 
	 * - Unknown [KEY_WORD]
	 */
	
	
	@Ignore @Test
	public void Help() throws Exception {
		
		assertHelpCommandBehavior("help", HelpCommand.MESSAGE_USAGE);
	}

	
	@Ignore @Test
	public void Null_Help_Parameters() throws Exception {
		String invalidCommand = "       ";
		assertHelpCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
	}

	
	@Ignore @Test
	public void execute_unknownCommandWord() throws Exception {
		String unknownCommand = "uicfhmowqewca";
		assertHelpCommandBehavior(unknownCommand,String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
	}
	
	
	/*
	 * 1) valid help parameters 
	 * - add
	 * - delete
	 * - list 
	 * - find 
	 * - edit 
	 * - mark 
	 * - clear 
	 * - exit 
	 */
	
	@Ignore @Test
	public void execute_addCommand() throws Exception {
		String addCommand = "add";
		assertHelpCommandBehavior(addCommand, AddCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_deleteCommand() throws Exception {
		String deleteCommand = "delete";
		assertHelpCommandBehavior(deleteCommand, DeleteCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_listCommand() throws Exception {
		String listCommand = "list";
		assertHelpCommandBehavior(listCommand, ListCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_findCommand() throws Exception {
		String findCommand = "find";
		assertHelpCommandBehavior(findCommand, FindCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_editCommand() throws Exception {
		String editCommand = "edit";
		assertHelpCommandBehavior(editCommand, EditCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_markCommand() throws Exception {
		String markCommand = "mark";
		assertHelpCommandBehavior(markCommand, MarkCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_clearCommand() throws Exception {
		String clearCommand = "clear";
		assertHelpCommandBehavior(clearCommand, ClearCommand.MESSAGE_USAGE);
	}
	
	@Ignore @Test
	public void execute_exitCommand() throws Exception {
		String exitCommand = "exit";
		assertHelpCommandBehavior(exitCommand, ExitCommand.MESSAGE_USAGE);
	}
	
}
