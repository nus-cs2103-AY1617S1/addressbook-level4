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
	public void correctTaskArgumentsShouldReturnTrue() {

		String arguments = "complete cs2103t by : 10-08-2016 1900 n : important";

		assertTrue(AddParser.isTask(arguments));

	}

	
	@Test
	public void correctEventArgumentsShouldReturnTrue(){
		
		String arguments = "meet prof damith from: 10-08-2016 1900 to: 11-08-2016 1900 n:he is fierce";
		
		assertTrue(AddParser.isEvents(arguments));
	}
	
	@Test
	public void wrongEventArgumentShouldReturnFalse(){
		
		String arguments = "meet prof damith from: 10-08-2016 1900 by: 11-08-2016 1900 n:he is fierce";
	
		assertFalse(AddParser.isEvents(arguments));
	
	}
	
	@Test
	public void taskShouldReturnTaskType(){
		
		String arguments = "complete cs2103t by : 10-08-2016 1900 n : important";

		try{
			assertEquals(Activity.TASK_TYPE, AddParser.parseCommand(arguments).get(0));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void eventShouldRetunEventType(){
		
		String arguments = "meet prof damith from: 10-08-2016 1900 to: 11-08-2016 1900 n:he is fierce";
		try{
			assertEquals("event", AddParser.parseCommand(arguments).get(0));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void floatingTaskShouldReturnFloatingTaskType(){
		
		String arguments = "complete cs2103t n:important";
		
		try{
			assertEquals(Activity.FLOATING_TASK_TYPE, AddParser.parseCommand(arguments).get(0));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}

	}
	
	@Test
	public void parseEventCommandShouldReturnCorrectArguments(){
		
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
	public void parseEventCommandWithoutNotesShouldReturnCorrectArguments(){
		
		String arguments = "meet prof damith from: 08-10-2016 1900 to: 08-11-2016 1900";
		try{
			assertEquals("meet prof damith", AddParser.parseCommand(arguments).get(1));
			assertEquals(null, AddParser.parseCommand(arguments).get(2));
			assertEquals("10-08-2016", AddParser.parseCommand(arguments).get(3));
			assertEquals("1900", AddParser.parseCommand(arguments).get(4));
			assertEquals("11-08-2016", AddParser.parseCommand(arguments).get(5));
			assertEquals("1900", AddParser.parseCommand(arguments).get(6));
		} catch (IllegalValueException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void parseTaskCommandShouldReturnCorrectArguments(){
	
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
	public void parseTaskCommandWithoutNotesShouldReturnCorrectArguments(){
		
		String arguments = "complete cs2103t by : 10-08-2016 1900";
		try{
			assertEquals("complete cs2103t", AddParser.parseCommand(arguments).get(1));
			assertEquals(null, AddParser.parseCommand(arguments).get(2));
			assertEquals("08-10-2016", AddParser.parseCommand(arguments).get(3));
			assertEquals("1900", AddParser.parseCommand(arguments).get(4));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void parseFloatingTaskCommandShouldReturnCorrectArguments(){
		
		String arguments = "complete cs2103t n:important";		
		try {
			assertEquals("complete cs2103t", AddParser.parseCommand(arguments).get(1));
			assertEquals("important", AddParser.parseCommand(arguments).get(2));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void parseFloatingTaskCommandWithoutNotesShouldReturnCorrectArguments(){
		
		String arguments = "complete cs2103t";
		try{
			assertEquals("complete cs2103t", AddParser.parseCommand(arguments).get(1));
			assertEquals(null, AddParser.parseCommand(arguments).get(2));
		} catch (IllegalValueException e){
			System.out.println(e.getMessage());
		}
		
	}
	
}
