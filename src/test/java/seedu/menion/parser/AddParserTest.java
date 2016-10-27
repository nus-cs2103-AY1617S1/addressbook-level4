package seedu.menion.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.parser.AddParser;
import seedu.menion.model.activity.Activity;
//@@author A0139277U
public class AddParserTest {

	
	@Test
	public void checkIsTask_returnsTrue() {

		String arguments = "complete cs2103t by : 10-08-2016 1900 n : important";

		assertTrue(AddParser.isTask(arguments));

	}

	
	@Test
	public void checkIsEvent_returnsTrue(){
		
		String arguments = "meet prof damith from: 10-08-2016 1900 to: 11-08-2016 1900 n:he is fierce";
		
		assertTrue(AddParser.isEvents(arguments));
	}
	
	@Test
	public void checkIsEvent_returnsFalse(){
		
		String arguments = "meet prof damith from: 10-08-2016 1900 by: 11-08-2016 1900 n:he is fierce";
	
		assertFalse(AddParser.isEvents(arguments));
	
	}
	
	@Test
	public void checkTypeOfActivity_returnsTask(){
		
		String arguments = "complete cs2103t by : 10-08-2016 1900 n : important";

		try{
			assertEquals("task", AddParser.parseCommand(arguments).get(0));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void checkTypeOfActivity_returnsEvents(){
		
		String arguments = "meet prof damith from: 10-08-2016 1900 to: 11-08-2016 1900 n:he is fierce";
		try{
			assertEquals("event", AddParser.parseCommand(arguments).get(0));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void checkTypeOfActivity_returnsFloatingTask(){
		
		String arguments = "complete cs2103t n:important";
		
		try{
			assertEquals(Activity.FLOATING_TASK_TYPE, AddParser.parseCommand(arguments).get(0));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}

	}
	
	@Test
	public void checkEventArguments_returnsCorrectArguments(){
		
		String arguments = "meet prof damith from: 08-10-2016 1900 to: 08-11-2016 1900 n:he is fierce";
		try {
			assertEquals("meet prof damith", AddParser.parseCommand(arguments).get(1));
			assertEquals("he is fierce", AddParser.parseCommand(arguments).get(2));
			assertEquals("10-08-2016", AddParser.parseCommand(arguments).get(3));
			assertEquals("1900", AddParser.parseCommand(arguments).get(4));
			assertEquals("11-08-2016", AddParser.parseCommand(arguments).get(5));
			assertEquals("1900", AddParser.parseCommand(arguments).get(6));
		} catch (IllegalValueException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void checkTaskArguments_returnsCorrectArguments(){
	
		String arguments = "complete cs2103t by : 10-08-2016 1900 n : important";
		try{
			assertEquals("complete cs2103t", AddParser.parseCommand(arguments).get(1));
			assertEquals("important", AddParser.parseCommand(arguments).get(2));
			assertEquals("08-10-2016", AddParser.parseCommand(arguments).get(3));
			assertEquals("1900", AddParser.parseCommand(arguments).get(4));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void checkFloatingTaskArguments_returnsCorrectArguments(){
		
		String arguments = "complete cs2103t n:important";		
		try {
			assertEquals("complete cs2103t", AddParser.parseCommand(arguments).get(1));
			assertEquals("important", AddParser.parseCommand(arguments).get(2));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
		
	}


	
}
