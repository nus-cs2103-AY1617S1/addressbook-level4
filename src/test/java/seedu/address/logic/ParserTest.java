package seedu.address.logic;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.MainParser;
import seedu.address.model.task.Priority;

/**
 * Tests the Parser.
 * 
 * @author A0139661Y
 */

public class ParserTest {
	
	private MainParser parser;
	private static final LocalDate NO_DATE = LocalDate.parse(MainParser.NO_DATE_DEFAULT, DateTimeFormatter.ISO_LOCAL_DATE);
	private static final LocalTime NO_TIME = LocalTime.parse(MainParser.NO_TIME_DEFAULT, DateTimeFormatter.ISO_LOCAL_TIME);
	private static final String NO_PRIORITY = "";
	private static final Set NO_TAGS = Collections.EMPTY_SET;
	
	@Test
	public void addFloating() throws IllegalValueException {
		parser = MainParser.getInstance();
		assertEquals(((AddCommand) parser.parseCommand("add New Task")).getTask(),
				(new AddCommand("New Task", 
						NO_DATE,
						NO_TIME,
						NO_PRIORITY, 
						NO_TAGS).getTask()));
	}
	
	@Test
	public void addFloatingPriority() throws IllegalValueException {
		parser = MainParser.getInstance();
		assertEquals(((AddCommand) parser.parseCommand("add New Task /high")).getTask(),
				(new AddCommand("New Task", 
						NO_DATE,
						NO_TIME,
						Priority.HIGH, 
						NO_TAGS).getTask()));
	}
	
	@Test
	public void addDateTimePriority() throws IllegalValueException {
		parser = MainParser.getInstance();
		assertEquals(((AddCommand) parser.parseCommand("add New Task tomorrow noon /high")).getTask(),
				(new AddCommand("New Task", 
						LocalDate.parse("2016-10-11", DateTimeFormatter.ISO_LOCAL_DATE), 
						LocalTime.parse("12:00:00", DateTimeFormatter.ISO_LOCAL_TIME), 
						Priority.HIGH, 
						NO_TAGS).getTask()));
	}
	
	@Test
	public void addDatePriority() throws IllegalValueException {
		parser = MainParser.getInstance();
		assertEquals(((AddCommand) parser.parseCommand("add New Task the day after tomorrow /high")).getTask(),
				(new AddCommand("New Task", 
						LocalDate.parse("2016-10-11", DateTimeFormatter.ISO_LOCAL_DATE), 
						NO_TIME, 
						Priority.HIGH, 
						NO_TAGS).getTask()));
	}
}
