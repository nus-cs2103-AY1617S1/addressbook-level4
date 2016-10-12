package seedu.address.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.task.TaskType;


public class ParserTest {

	private Parser parser;
	private IncorrectCommand incorrectCommand;
	private AddCommand addCommand;
	private ListCommand listCommand;
	private DeleteCommand deleteCommand;
	
	public ParserTest() throws IllegalValueException {
		parser = new Parser();
		incorrectCommand = new IncorrectCommand("test");
		addCommand = new AddCommand("test adding someday");
		listCommand = new ListCommand();
		deleteCommand = new DeleteCommand(new int[]{1});
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
	public void parseCommand_addEventNoDate_incorrectCommandReturned() {
		String userInput = "add event ' party' from 8:00 to 9:00";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventNoEndTime_incorrectCommandReturned() {
		String userInput = "add event ' party hehehe yay' from 8:00 on 12/12/12";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventNoName_incorrectCommandReturned() {
		String userInput = "add event from 8:00 to 10:00 on 12/12/12";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventInvalidOrder_incorrectCommandReturned() {
		String userInput = "add event from 8:00 'party' to 10:00 on 12/12/12";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}

	@Test
	public void parseCommand_addEventValidOrder_addCommandReturned() {
		String userInput = "add event 'party' from 5:00 to 27:00 on 12/12/12";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder2_addCommandReturned() {
		String userInput = "add event from 8:00 to 10:00 'party' on 12/190/12";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addEventValidOrder3_addCommandReturned() {
		String userInput = "add event on 12/12/12 from 8:00 to 10:00 'party'";
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
	public void parseCommand_addDeadlineNoDate_incorrectCommandReturned() {
		String userInput = "add deadline 'submission' at 5:00pm";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoTime_incorrectCommandReturned() {
		String userInput = "add deadline 'submission all day' by 36/2/";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineInvalidOrder_incorrectCommandReturned() {
		String userInput = "add deadline by 25/16/16 'submission' 4am";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder_addCommandReturned() {
		String userInput = "add deadline 'submission' by 04:00 25/12/16";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder2_addCommandReturned() {
		String userInput = "add deadline 'submission' by 25/12/16 04:00";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder3_addCommandReturned() {
		String userInput = "add deadline by 25/12/16 04:00 'submission' ";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	
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
		String userInput = "add someday ' '";
		Command command = parser.parseCommand(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayValidOrder_addCommandReturned() {
		String userInput = "add someday 'dance'";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayValidOrder2_addCommandReturned() {
		String userInput = "add 'dance again' someday";
		Command command = parser.parseCommand(userInput);

		assertEquals(addCommand.getClass(), command.getClass());
	}
	
	
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
		String userInput = "list   deadline not-done";
		Command command = parser.parseCommand(userInput);

		assertEquals(listCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_listValidOrder2_listCommandReturned() {
		String userInput = "list not-done	 someday";
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
		String userInput = "delete 2";
		Command command = parser.parseCommand(userInput);

		assertEquals(deleteCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_delValidIndices_deleteCommandReturned() {
		String userInput = "delete 3 2";
		Command command = parser.parseCommand(userInput);

		assertEquals(deleteCommand.getClass(), command.getClass());
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

		assertEquals(addCommand.getClass(), command.getClass());
	}
}
