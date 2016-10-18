package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


//@@ author A0141019U
public class DateParserTest {
	
	LocalDateTime now = LocalDateTime.now();
	LocalDateTime christmas430pm = LocalDateTime.of(2016, 12, 25, 16, 30);

	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	@Test
	public void ddmmyyHHmm_valid_valueAsExpected() throws ParseException {
		String userInput = "25-12-16 16:30";
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
	public void ddMMMyyyyHHmm_valid_valueAsExpected() throws ParseException {
		String userInput = "25 dec 2016 16:30";
		LocalDateTime date = DateParser.parse(userInput);

		assertEquals(christmas430pm, date);
	}
	
	@Test
	public void ddMMMyyyyHHmm_monthWordWrong_throwsParseException() throws ParseException {
		thrown.expect(ParseException.class);
		thrown.expectMessage("Month is not an integer or one of the standard 3 letter abbreviations.");
		
		String userInput = "25 pop 2016 16:30";
		DateParser.parse(userInput);
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
}
