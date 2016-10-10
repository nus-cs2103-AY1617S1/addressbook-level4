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
}
