package seedu.menion.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.ListCommand;
import seedu.menion.model.activity.ActivityDate;

//@@author A0139277U
public class ListCommandTest {

	@Test
	public void checkListType_returnsListTypeAll () throws IllegalValueException{
		
		String argument = "all";
		ListCommand list = new ListCommand(argument);
		
		assertEquals(list.LIST_ALL, list.checkListType(argument));
		
	}

	@Test
	public void checkListType_returnsListTypeDate() throws IllegalValueException{
		
		String argument = "19-08-1994";
		ListCommand list = new ListCommand(argument);
		
		assertEquals(list.LIST_DATE, list.checkListType(argument));
	}
	
	@Test
	public void checkListType_returnsListTypeMonth() throws IllegalValueException {
		
		String argument = "deCemBer";
		ListCommand list = new ListCommand(argument);
		
		assertEquals(list.LIST_MONTH, list.checkListType(argument));
		
	}
	
	@Test
	public void checkListTypeNoArguments_returnsListTypeAll() throws IllegalValueException {
		
		String argument = "";
		ListCommand list = new ListCommand(argument);
		
		assertEquals(list.LIST_ALL, list.checkListType(argument));
	}
	
	

	
	
}
