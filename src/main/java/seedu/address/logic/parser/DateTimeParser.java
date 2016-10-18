package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.*;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

/**
 * For parsing dates and times in Sudowudo command input
 * @author darren
 */
public class DateTimeParser {
    // the part of the command that contains the temporal part of the command
    private String datetime;
    
    // natty parser object
    // careful of name collision with our own Parser object
    private PrettyTimeParser parser;
    
    // result from parser
    private List<DateGroup> dategroups;
    private List<Date> dates;
    
    DateTimeParser(String input) {
        assert input != null;
        assert input.isEmpty() != true;

        this.datetime = input;
        this.parser = new PrettyTimeParser();

        // perform natty parsing
        this.dategroups = this.parser.parseSyntax(input);
        this.dates = this.parser.parse(input);
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
            return extractStartDate();
        }

        return Date2LocalDateTime(this.dates.get(1));
    }
    
    public boolean isRecurring() {
        return this.dategroups.get(0).isRecurring();
    }
    
    public LocalDateTime getRecurEnd() {
        return Date2LocalDateTime(this.dategroups.get(0).getRecursUntil());
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

    public DateGroup getDateGroup(int index) {
        return this.dategroups.get(index);
    }

    public String getDateTime() {
        return this.datetime;
    }
    
}