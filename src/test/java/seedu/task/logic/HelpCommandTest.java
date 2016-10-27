package seedu.task.logic;

import org.junit.After;
import org.junit.Before;
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
import seedu.task.logic.commands.UndoCommand;

/**
 * Responsible for testing the execution of HelpCommand
 * 
 * @author Yee Heng
 */

//@@author A0125534L
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

	// ------------------------Tests for invalid arguments----------------
	/*
	 * Command input: "help" , "help [KEY_WORD]"
	 * 
	 * Valid arguments [KEY_WORD]: "null", "add", "delete", "edit", "list",
	 * "mark", "find", "undo", "clear", "exit"
	 * 
	 * 
	 * Invalid arguments to test: [KEY_WORD]: "4", "/r", "$", "adds"
	 * 
	 * 
	 */

	@Test
	public void executeHelpInvalidArgsFormat() throws Exception {
		String expectedMessage = String.format(HelpCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help  4 ", expectedMessage);
		assertHelpCommandBehavior("help  /r ", expectedMessage);
		assertHelpCommandBehavior("help / r ", expectedMessage);
		assertHelpCommandBehavior("help $ ", expectedMessage);
		assertHelpCommandBehavior("help adds", expectedMessage);

	}

	// ------------------------Tests for valid inputs----------------
	/*
	 * 1) valid help [KEY_WORD] 
	 *  add  
	 *  delete 
	 *  list 
	 *  find 
	 *  edit 
	 *  mark 
	 *  undo
	 *  clear 
	 *  exit
	 */

	@Test
	public void executeHelpValidArgsFormat() throws Exception {
		assertHelpCommandBehavior("help add", AddCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help delete", DeleteCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help list", ListCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help find", FindCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help edit", EditCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help mark", MarkCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help undo", UndoCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help clear", ClearCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help exit", ExitCommand.MESSAGE_USAGE);

	}

}
