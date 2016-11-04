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
	public void parseDate_ddmmyyHHmm_valueAsExpected() throws ParseException {
		String userInput = "25-12-16 1630";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void parseDate_HHmmddmmyy_valueAsExpected() throws ParseException {
		String userInput = "16:30 25/12/16";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}

	// TODO note in user guide that both - and / are both allowed in the same date
	@Test
	public void parseDate_yyyymmddhhmmpm_valueAsExpected() throws ParseException {
		String userInput = "2016/12-25 4:30pm";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}

	@Test
	public void parseDate_ddMMMyyyyHHmm_valueAsExpected() throws ParseException {
		String userInput = "25-dec-2016 16:30";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}

	@Test
	public void parseDate_HHmmddMMMyyyy_valueAsExpected() throws ParseException {
		String userInput = "16:30 25-dec-2016";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}

	@Test
	public void parseDate_ddmmyyhhmmpm_valueAsExpected() throws ParseException {
		String userInput = "2/7/11 0400am";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime expected = LocalDateTime.of(2011, 7, 2, 4, 0);
		
		assertEquals(expected, date);
	}
	
	@Test
	public void parseDate_today5pm_valueAsExpected() throws ParseException {
		String userInput = "today 5pm";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime today5pm = LocalDateTime.now().withHour(17).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(today5pm, date);
	}
	
	@Test
	public void parseDate_oneAmNextWeek_valueAsExpected() throws ParseException {
		String userInput = "01:00 next week";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime oneAmNextWeek = LocalDateTime.now().plusDays(7).withHour(1).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(oneAmNextWeek, date);
	}
	
	@Test
	public void parseDate_nextWeek1am_valueAsExpected() throws ParseException {
		String userInput = "next week 01:00";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime oneAmNextWeek = LocalDateTime.now().plusDays(7).withHour(1).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(oneAmNextWeek, date);
	}
	
	@Test
	public void parseDate_tuesday630am_valueAsExpected() throws ParseException {
		String userInput = "tuesday 6:30 am";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime tuesday630 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).withHour(6).withMinute(30).truncatedTo(ChronoUnit.MINUTES);
		
		assertEquals(tuesday630, date);
	}
	
	@Test
	public void parseDate_saturday630am_valueAsExpected() throws ParseException {
		String userInput = "sat 6:30am ";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime saturday630 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(6).withMinute(30).truncatedTo(ChronoUnit.MINUTES);
		
		assertEquals(saturday630, date);
	}
	
	@Test
	public void parseDate_noDay1am_valueAsExpected() throws ParseException {
		String userInput = "01:00";
		LocalDateTime date = DateParser.parse(userInput);
		
		LocalDateTime oneAmNextWeek = LocalDateTime.now().withHour(1).truncatedTo(ChronoUnit.HOURS);
		
		assertEquals(oneAmNextWeek, date);
	}
	
	@Test
	public void parseDate_dateOutOfBounds_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Day is not within valid bounds 1 - 31 inclusive");
		
		String userInput = "32-12-16 16:30";
		DateParser.parse(userInput);
	}
	
	@Test
	public void parseDate_dateInvalidForMonth_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Date '30' is invalid for month entered.");
		
		String userInput = "30-2-16 16:30";
		DateParser.parse(userInput);
	}
	
	@Test
	public void parseDate_monthOutOfBounds_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Month is not within valid bounds 1 - 12 inclusive");
		
		String userInput = "2010-13-25 430 am";
		DateParser.parse(userInput);
	}
	
	@Test
	public void parseDate_monthWordWrong_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Month is not an integer or one of the standard 3 letter abbreviations.");
		
		String userInput = "25-pop-2016 16:30";
		DateParser.parse(userInput);
	}
}
