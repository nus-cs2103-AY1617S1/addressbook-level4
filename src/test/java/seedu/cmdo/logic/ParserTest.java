package seedu.cmdo.logic;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.logic.commands.AddCommand;
import seedu.cmdo.logic.parser.MainParser;
import seedu.cmdo.model.task.Priority;

/**
 * Tests the Parser for add commands.
 * 
 * Please do note that mixing relative/explicit time/date input is not allowed. 
 * 
 * @author A0139661Y
 */

@FixMethodOrder(MethodSorters.JVM)
public class ParserTest {
	
//	private MainParser parser = MainParser.getInstance();
//	private static final LocalDate NO_DATE = LocalDate.parse(MainParser.NO_DATE_DEFAULT, DateTimeFormatter.ISO_LOCAL_DATE);
//	private static final LocalTime NO_TIME = LocalTime.parse(MainParser.NO_TIME_DEFAULT, DateTimeFormatter.ISO_LOCAL_TIME);
//	private static final String NO_PRIORITY = "";
//	private static final Set NO_TAGS = Collections.EMPTY_SET;
//		
//	@Test
//	public void addFloating() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						NO_DATE,
//						NO_TIME,
//						NO_PRIORITY, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addFloatingPriority() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task /high")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						NO_DATE,
//						NO_TIME,
//						Priority.HIGH, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	
//	@Test
//	public void addExplicitTimePriority() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task at 6pm /high")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.now(), 
//						LocalTime.of(18, 00), 
//						Priority.HIGH, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addExplicitDate() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task 10/20/2016")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.of(2016, 10, 20),
//						NO_TIME,
//						NO_PRIORITY, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addExplicitDateTime() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task 10/20/2016 at 12pm")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.of(2016, 10, 20),
//						LocalTime.of(12, 00),
//						NO_PRIORITY, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addRelativeTime() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task in 2 hours")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.now(), 
//						LocalTime.now().plusHours(2), 
//						NO_PRIORITY, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addRelativeTimeEdgeCase() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task by 2 hours from 11pm")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.now().plusDays(1), 
//						LocalTime.of(23, 00).plusHours(2), 
//						NO_PRIORITY, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	/**
//	 * Note that when adding a task of relative date, the time it inherits is the time the task was added. 
//	 */
//	@Test
//	public void addRelativeDate() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task in 2 days")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.now().plusDays(2), 
//						LocalTime.now(),
//						NO_PRIORITY, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addRelativeDateTimePriority() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task tomorrow noon /high")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.now().plusDays(1),
//						LocalTime.of(12, 00),
//						Priority.HIGH, 
//						NO_TAGS).getTask().getAsText());
//	}
//	
//	@Test
//	public void addRelativeDatePriority() throws IllegalValueException {
//		parser = MainParser.getInstance();
//		assertEquals(((AddCommand) parser.parseCommand("add New Task the day after tomorrow /high")).getTask().getAsText(),
//				new AddCommand("New Task", 
//						LocalDate.now().plusDays(2),
//						NO_TIME, 
//						Priority.HIGH, 
//						NO_TAGS).getTask().getAsText());
//	}
	

	

}
