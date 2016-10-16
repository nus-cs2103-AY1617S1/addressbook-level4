package seedu.address.logic.parser;

import java.time.*;
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
    
    DateTimeParser(String temporality) {
        assert temporality != null;
        assert temporality.isEmpty() != true;

        this.datetime = temporality;
        this.parser = new com.joestelmach.natty.Parser();
    }
    
}