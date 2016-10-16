package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

/**
 * For parsing dates and times in Sudowudo command input
 * @author darren
 */
public class DateTimeParser {
    // the part of the command that contains the temporal part of the command
    private String datetime;
    
    // natty parser object
    // careful of name collision with our own Parser object
    private com.joestelmach.natty.Parser parser;
    
    DateTimeParser(String input) {
        assert input != null;
        assert input.isEmpty() != true;

        this.datetime = input;
        this.parser = new com.joestelmach.natty.Parser();
    }
    
    /**
     * produces a list of java.time.LocalDateTime from natty's parsing
     * @return
     * @author darren
     */
    public List<LocalDateTime> parseInput() {
        assert this.datetime != null;

        // natty-side parsing
        List<Date> dateTokens = this.parser.parse(this.datetime) // produces List<DateGroup>
                                            .get(0) // produces DateGroup
                                            .getDates(); // produces List<Date>
        
        if(dateTokens.isEmpty()) {
            throw new IllegalStateException("No dates found in token!");
        }

        List<LocalDateTime> datetimes = new ArrayList<LocalDateTime>();
        
        // convert Date to LocalDateTime
        for(Date date : dateTokens) {
            datetimes.add(Date2LocalDateTime(date));
        }

        return datetimes;
    }
    
    /**
     * helper method for parseInput()
     * converts a java.util.Date object to java.time.LocalDateTime object
     * 
     * @param date
     * @return
     * @author darren
     */
    private LocalDateTime Date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public String getDateTime() {
        return this.datetime;
    }
    
}