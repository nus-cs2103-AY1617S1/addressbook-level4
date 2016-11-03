package seedu.address.logic.parser;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.ModelManager;

//@@author A0141019U
public class ParserTest {

	private final Parser parser;
	private final IncorrectCommand incorrectCommand;
	private final AddCommand addCommand;
	private final ListCommand listCommand;
	private final DeleteCommand deleteCommand;
	private final ChangeStatusCommand changeStatusCommand;
	private final EditCommand editCommand;
	private final UndoCommand undoCommand;
	private final RedoCommand redoCommand;
	
	public ParserTest() throws IllegalValueException {
		parser = new Parser(new ModelManager());
		incorrectCommand = new IncorrectCommand("test");
		addCommand = new AddCommand("test adding someday");
		listCommand = new ListCommand();
		deleteCommand = new DeleteCommand(new int[]{1});
		changeStatusCommand = new ChangeStatusCommand(new int[]{1}, "done");
		editCommand = new EditCommand(1, Optional.of("editing"), 
				Optional.of(LocalDateTime.now()), Optional.of(LocalDateTime.now()),
				false, false);
		undoCommand = new UndoCommand();
		redoCommand = new RedoCommand();
	}
	
	@Test
	public void parseCommand_whitespaceInput_incorrectCommandReturned() {
		String userInput = "       ";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addNoArgs_incorrectCommandReturned() {
		String userInput = "add";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	
	/*
	 * Tests for the `add event` command
	 */
	@Test
	public void parseCommand_addEventNoArgs_incorrectCommandReturned() {
		String userInput = "add event";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventWhitespaceName_incorrectCommandReturned() {
		String userInput = "add event ' 	 '";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventNoEndTime_incorrectCommandReturned() {
		String userInput = "add event ' party hehehe yay' from 8:00 on 12-12-12";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventNoName_incorrectCommandReturned() {
		String userInput = "add event from 8:00 to 10:00 on 12-12-12";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	

	@Test
	public void parseCommand_addEventNoDate_addCommandReturned() {
		String userInput = "add ' party' from 8:00 to 9:00";
		Command command = parser.parseCommand(userInput);
		
		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder_addCommandReturned() {
		String userInput = "add 'party' from 5:00 to 5:00 on 12-12-12";
		Command command = parser.parseCommand(userInput);
		
		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder2_addCommandReturned() {
		String userInput = "add 'party' on 12-oct-12 from 8:00 to 10:00";
		Command command = parser.parseCommand(userInput);
		
		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder3_addCommandReturned() {
		String userInput = "add 'party' from 8:00 12-5-13 to 10:00 13-5-13";
		Command command = parser.parseCommand(userInput);
		
		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder4_addCommandReturned() {
		String userInput = "add 'party' from 8:00 12-oct-13 to 10:00 13-oct-13";
		Command command = parser.parseCommand(userInput);
		
		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder5_addCommandReturned() {
		String userInput = "add from 8:00 'party' to 10:00 on 12-12-12";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder6_addCommandReturned() {
		String userInput = "add from 8:00 to 10:00 'party' on 12-10-12";
		Command command = parser.parseCommand(userInput);
		
		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	
	/*
	 * Tests for the `add deadline` command
	 */
	@Test
	public void parseCommand_addDeadlineNoArgs_incorrectCommandReturned() {
		String userInput = "add deadline";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoBy_incorrectCommandReturned() {
		String userInput = "add 'submission' 5:00pm";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoTime_incorrectCommandReturned() {
		String userInput = "add deadline 'submission all day' by 16-2-";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineInvalidOrder_incorrectCommandReturned() {
		String userInput = "add by 25-16-16 'submission' 4am";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder_addCommandReturned() {
		String userInput = "add 'submission' by 04:00 25-12-16";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder2_addCommandReturned() {
		String userInput = "add 'submission' by 25-12-16 04:00";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
//	
//	@Test
//	public void parseCommand_addDeadlineValidOrder3_addCommandReturned() {
//		String userInput = "add by 25-12-16 04:00 'submission' ";
//		Command command = parser.parseCommand(userInput);
//
//		assertEquals(addCommand.getClass(), command.getClass());
//	}
	
	
	/*
	 * Tests for the `add someday` command
	 */
	@Test
	public void parseCommand_addSomedayNoArgs_incorrectCommandReturned() {
		String userInput = "add someday";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayWhitespaceName_incorrectCommandReturned() {
		String userInput = "add ' '";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayValidOrder_addCommandReturned() {
		String userInput = "add 'dance'";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
//	@Test
//	public void parseCommand_addSomedayValidOrder2_addCommandReturned() {
//		String userInput = "add 'dance again' someday";
//		Command command = parser.parseCommand(userInput);
//
//		assertEquals(addCommand.getClass(), command.getClass());
//	}
	
	
	/*
	 * Tests for the `list` command
	 */
	@Test
	public void parseCommand_listInvalidArgs_incorrectCommandReturned() {
		String userInput = "list hello";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_listNoArgs_listCommandReturned() {
		String userInput = "list";
		Command command = parser.parseCommand(userInput);

		assertEquals(listCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_listDone_listCommandReturned() {
		String userInput = "list done";
		Command command = parser.parseCommand(userInput);

		assertEquals(listCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_listValidEvent_listCommandReturned() {
		String userInput = "list event";
		Command command = parser.parseCommand(userInput);

		assertEquals(listCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_listValidOrder_listCommandReturned() {
		String userInput = "list   deadline pending";
		Command command = parser.parseCommand(userInput);

		assertEquals(listCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_listValidOrder2_listCommandReturned() {
		String userInput = "list pending	 someday";
		Command command = parser.parseCommand(userInput);

		assertEquals(listCommand.getClass(), command.getClass());
	}
	
	/*
	 * Tests for the `del` command
	 */
	@Test
	public void parseCommand_delNonIntegerIndex_incorrectCommandReturned() {
		String userInput = "delete 1 r 5";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_delNegativeIndex_incorrectCommandReturned() {
		String userInput = "delete -3";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_delZeroIndex_incorrectCommandReturned() {
		String userInput = "delete 0";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_delValidIndex_deleteCommandReturned() {
		String userInput = "del 2";
		Command command = parser.parseCommand(userInput);

		assertEquals(deleteCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_delValidIndices_deleteCommandReturned() {
		String userInput = "del 3 2";
		Command command = parser.parseCommand(userInput);

		assertEquals(deleteCommand.getClass(), command.getClass());
	}

	/*
	 * Tests for the `done` and `pending` commands
	 */
	@Test
	public void parseCommand_doneNonIntegerIndex_incorrectCommandReturned() {
		String userInput = "done 1 r 5";
		Command command = parser.parseCommand(userInput);
		assertEquals(incorrectCommand.getClass(), command.getClass());
		
		userInput = "pending 1 r 5";
		command = parser.parseCommand(userInput);
		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_doneNegativeIndex_incorrectCommandReturned() {
		String userInput = "done -3";
		Command command = parser.parseCommand(userInput);
		assertEquals(incorrectCommand.getClass(), command.getClass());
		
		userInput = "pending -3";
		command = parser.parseCommand(userInput);
		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_doneZeroIndex_incorrectCommandReturned() {
		String userInput = "done 0";
		Command command = parser.parseCommand(userInput);
		assertEquals(incorrectCommand.getClass(), command.getClass());
		
		userInput = "pending 0";
		command = parser.parseCommand(userInput);
		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_doneValidIndex_changeStatusCommandReturned() {
		String userInput = "done 2";
		Command command = parser.parseCommand(userInput);
		assertEquals(changeStatusCommand.getClass(), command.getClass());
		
		userInput = "pending 2";
		command = parser.parseCommand(userInput);
		assertEquals(changeStatusCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_doneValidIndices_changeStatusCommandReturned() {
		String userInput = "done 3 2";
		Command command = parser.parseCommand(userInput);
		assertEquals(changeStatusCommand.getClass(), command.getClass());
		
		userInput = "pending 3 2";
		command = parser.parseCommand(userInput);
		assertEquals(changeStatusCommand.getClass(), command.getClass());
	}
	
	/*
	 * Tests for the `edit` command
	 */
	@Test
	public void parseCommand_editNoArgs_incorrectCommandReturned() {
		String userInput = "edit";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_editNonIntIndex_incorrectCommandReturned() {
		String userInput = "edit j 'new name'";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_editNegIndex_incorrectCommandReturned() {
		String userInput = "edit -3 'new name'";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_editValid_editCommandReturned() {
		String userInput = "edit 1 'new name'";
		Command command = parser.parseCommand(userInput);

		assertEquals(editCommand.getClass(), command.getClass());
	}
	
	/*
	 * Tests for the `undo` and `redo` commands
	 */
	
	@Test
	public void parseCommand_undoExtraArgs_undoCommandReturned() {
		String userInput = "undo blah";
		Command command = parser.parseCommand(userInput);

		assertEquals(undoCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_undoValid_undoCommandReturned() {
		String userInput = "undo";
		Command command = parser.parseCommand(userInput);

		assertEquals(undoCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_redoExtraArgs_redoCommandReturned() {
		String userInput = "redo blah";
		Command command = parser.parseCommand(userInput);

		assertEquals(redoCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_redoValid_undoCommandReturned() {
		String userInput = "redo";
		Command command = parser.parseCommand(userInput);

		assertEquals(redoCommand.getClass(), command.getClass());
	}
}
