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
    
    // result from parser
    private List<DateGroup> dategroups;
    private List<Date> dates;
    
    DateTimeParser(String input) {
        assert input != null;
        assert input.isEmpty() != true;

        this.datetime = input;
        this.parser = new com.joestelmach.natty.Parser();

        // perform natty parsing
        this.dategroups = this.parser.parse(input);
        this.dates = this.dategroups.get(0).getDates();
    }
    
    public LocalDateTime extractStartDate() {
        assert this.dates != null;

        if(this.dategroups.isEmpty()) {
            return null;
        }

        return Date2LocalDateTime(this.dates.get(0));
    }
    
    public LocalDateTime extractEndDate() {
        assert this.dates != null;

        if(this.dates.size() < 2) {
            return null;
        }

        return Date2LocalDateTime(this.dates.get(1));
    }
    
    /**
     * helper method for casting java.util.Date to java.time.LocalDateTime
     * safely
     * 
     * @param date
     * @return
     * @author darren
     */
    public static LocalDateTime Date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public String getDateTime() {
        return this.datetime;
    }
}