//@@author A0139772U
package seedu.whatnow.logic.parser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ParserTest {
    @Test
    public void formatDate_dateHyphenFormat_dateSlashFormatReturned() {
        assertEquals(Parser.formatDate("12-12-2222"), "12/12/2222");
    }
    
    @Test
    public void formatDate_dateDotFormat_dateSlashFormatReturned() {
        assertEquals(Parser.formatDate("12.12.2222"), "12/12/2222");
    }
    @Test
    public void formatDate_NoDelimiterFormat_dateSlashFormatReturned() {
        assertEquals(Parser.formatDate("12122222"), "12122222");
    }
    
    @Test
    public void formatTime_timeNoDelimiterFormat_timeColonFormatReturned() {
        assertEquals(Parser.formatTime("12", "am"), "12:00am");
    }
    
    @Test
    public void formatTime_timeDotFormat_timeColonFormatReturned() {
        assertEquals(Parser.formatTime("12.00", "am"), "12:00am");
    }
    
    @Test
    public void formatTime_colonNoPeriod_timeColonFormatReturned() {
        assertEquals(Parser.formatTime("12:00", "am"), "12:00am");
    }
    
    @Test
    public void formatTime_stringTimeAM_timeColonFormatReturned() {
        assertEquals(Parser.formatTime("12am"), "12:00am");
    }
    
    @Test
    public void formatTime_stringTimePM_timeColonFormatReturned() {
        assertEquals(Parser.formatTime("1pm"), "01:00pm");
    }
    
    @Test
    public void validateDate_hyphenFormat_isValidFormat() {
        Parser parser = new Parser();
        assertEquals(parser.setDate(1, "12-12-2222"), true);
    }
    
    @Test
    public void validateDate_dotFormat_isValidFormat() {
        Parser parser = new Parser();
        assertEquals(parser.setDate(1, "12.12.2222"), true);
    }
    
    @Test
    public void getSubDate_date_returnForwardSlashAppended() {
        Parser parser = new Parser();
        assertEquals(parser.getSubDate("12/12/2222"), null);
    }
    
    @Test
    public void getSubDate_dateWithSuffix_returnForwardSlashAppended() {
        Parser parser = new Parser();
        assertEquals(parser.getSubDate("12th"), "12/");
    }
    
    @Test
    public void getSubDate_monthInFull_returnForwardSlashAppended() {
        Parser parser = new Parser();
        assertEquals(parser.getSubDate("january"), "1/");
    }
    
    @Test
    public void getSubDate_monthInShort_returnForwardSlashAppended() {
        Parser parser = new Parser();
        assertEquals(parser.getSubDate("jan"), "1/");
    }
    
    @Test
    public void getSubDate_year_slashNotAppended() {
        Parser parser = new Parser();
        assertEquals(parser.getSubDate("2222"), "2222");
    }
    
    @Test
    public void getSubDate_rubbish_slashNotAppended() {
        Parser parser = new Parser();
        assertEquals(parser.getSubDate("rubbish"), null);
    }
    
    @Test
    public void validateTimeFormat_correctFormat_timeReturned() {
        Parser parser = new Parser();
        assertEquals(parser.setTime(1, "1:00pm"), true);
    }
    
    @Test
    public void validateTimeFormat_incorrectFormat_timeReturned() {
        Parser parser = new Parser();
        assertFalse("1:00pm".equals(parser.setTime(1, "1-00pm")));
    }
}

