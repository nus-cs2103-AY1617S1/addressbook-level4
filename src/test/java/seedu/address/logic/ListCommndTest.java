package seedu.address.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.ListCommand;

public class ListCommndTest {

	@Test
	public void checkListType_returnsCorrectTypeOfList () throws IllegalValueException{
		
		String argument = "all";
		ListCommand list = new ListCommand(argument);
		
		assertEquals(list.LIST_ALL, list.checkListType(argument));
		
	}

}
