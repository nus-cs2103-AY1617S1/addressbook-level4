package seedu.address.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.logic.parser.AddParser;

public class AddParserTest {

	
	@Test
	public void checkIsTask_returnsTrue() {

		String arguments = "by: 10-08-2016 1900";

		assertTrue(AddParser.isTask(arguments));

	}

	@Test
	public void checkIsTask_returnsFalse(){
		
		String arguments = "by: 10-08-16 9999";
		
		assertFalse(AddParser.isTask(arguments));
	}
	
	@Test
	public void checkIsEvent_returnsTrue(){
		
		String arguments = "from: 10-08-2016 1900 to: 11-08-2016 1900";
		
		assertTrue(AddParser.isEvents(arguments));
	}
	
	@Test
	public void checkIsEvent_returnsFalse(){
		
		String arguments = "from: 10-08-2016 1900 by: 11-08-2016 1900";
	
		assertFalse(AddParser.isEvents(arguments));
	
	}
	
	@Test
	public void checkTypeOfActivity_returnsTask(){
		
		String arguments = "by: 10-08-2016 1900";

		
		assertEquals("task", AddParser.parseCommand(arguments).get(0));
	}
	
	@Test
	public void checkTypeOfActivity_returnsEvents(){
		
		String arguments = "from: 10-08-2016 1900 to: 11-08-2016 1900";
		
		assertEquals("event", AddParser.parseCommand(arguments).get(0));
	}
	
	@Test
	public void checkTypeOfActivity_returnsFloatingTask(){
		
		String arguments = " ";
		
		assertEquals("floatingTask", AddParser.parseCommand(arguments).get(0));
	}
	
	@Test
	public void checkEventArguments_returnsCorrectArguments(){
		
		String arguments = "from: 10-08-2016 1900 to: 11-08-2016 1900";
		
		assertEquals("10-08-2016", AddParser.parseCommand(arguments).get(1));
		assertEquals("1900", AddParser.parseCommand(arguments).get(2));
		assertEquals("11-08-2016", AddParser.parseCommand(arguments).get(3));
		assertEquals("1900", AddParser.parseCommand(arguments).get(4));
	}
	
	@Test
	public void checkTaskArguments_returnsCorrectArguments(){
		
		String arguments = "by: 10-08-2016 1900";
		
		assertEquals("10-08-2016", AddParser.parseCommand(arguments).get(1));
		assertEquals("1900", AddParser.parseCommand(arguments).get(2));
		
	}
	
	
}
