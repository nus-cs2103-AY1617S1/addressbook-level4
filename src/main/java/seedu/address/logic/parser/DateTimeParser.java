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
     * wrapper for natty's parser
     * @return
     * @author darren
     */
    public List<DateGroup> parseInput() {
        assert this.datetime != null;
        assert this.parser != null;

        // natty-side parsing
        return parser.parse(this.datetime);
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