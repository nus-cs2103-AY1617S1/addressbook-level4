package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


//@@author A0141019U
public class DateParserTest {
	
	LocalDateTime now = LocalDateTime.now();
	LocalDateTime christmas430pm = LocalDateTime.of(2016, 12, 25, 16, 30);

	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	@Test
	public void ddmmyyHHmm_valid_valueAsExpected() throws ParseException {
		String userInput = "25-12-16 1630";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void ddmmyyHHmm_validOrder2_valueAsExpected() throws ParseException {
		String userInput = "16:30 25-12-16";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void ddmmyyHHmm_dateOutOfBounds_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Day is not within valid bounds 1 - 31 inclusive");
		
		String userInput = "32-12-16 16:30";
		DateParser.parse(userInput);
	}
	
	@Test
	public void yyyymmddhhmmpm_valid_valueAsExpected() throws ParseException {
		String userInput = "2016-12-25 4:30pm";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void yyyymmddhhmmpm_monthOutOfBounds_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Month is not within valid bounds 1 - 12 inclusive");
		
		String userInput = "2010-13-25 430 am";
		DateParser.parse(userInput);
	}
	
	@Test
	public void ddMMMyyyyHHmm_valid_valueAsExpected() throws ParseException {
		String userInput = "25-dec-2016 16:30";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void ddMMMyyyyHHmm_validOrder2_valueAsExpected() throws ParseException {
		String userInput = "16:30 25-dec-2016";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void ddMMMyyyyHHmm_monthWordWrong_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Month is not an integer or one of the standard 3 letter abbreviations.");
		
		String userInput = "25-pop-2016 16:30";
		DateParser.parse(userInput);
	}
	
	@Test
	public void ddmmyyhhmmpm_valid_valueAsExpected() throws ParseException {
		String userInput = "2-7-11 0400am";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime expected = LocalDateTime.of(2011, 7, 2, 4, 0);
		
		assertEquals(expected, date);
	}
	
	@Test
	public void today5pm_valid_valueAsExpected() throws ParseException {
		String userInput = "today 5pm";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime today5pm = LocalDateTime.now().withHour(17).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(today5pm, date);
	}
	
	@Test
	public void oneAmNextWeek_valid_valueAsExpected() throws ParseException {
		String userInput = "01:00 next week";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime oneAmNextWeek = LocalDateTime.now().plusDays(7).withHour(1).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(oneAmNextWeek, date);
	}
	
	@Test
	public void nextWeek1am_valid_valueAsExpected() throws ParseException {
		String userInput = "next week 01:00";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime oneAmNextWeek = LocalDateTime.now().plusDays(7).withHour(1).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(oneAmNextWeek, date);
	}
	
	@Test
	public void tuesday630am_valid_valueAsExpected() throws ParseException {
		String userInput = "tuesday 6:30 am";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime tuesday630 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).withHour(6).withMinute(30).truncatedTo(ChronoUnit.MINUTES);
		
		assertEquals(tuesday630, date);
	}
	
	@Test
	public void saturday630am_valid_valueAsExpected() throws ParseException {
		String userInput = "sat 6:30am ";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime saturday630 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(6).withMinute(30).truncatedTo(ChronoUnit.MINUTES);
		
		assertEquals(saturday630, date);
	}
	
	@Test
	public void noDay1am_valid_valueAsExpected() throws ParseException {
		String userInput = "01:00";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime oneAmNextWeek = LocalDateTime.now().withHour(1).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(oneAmNextWeek, date);
	}
}
