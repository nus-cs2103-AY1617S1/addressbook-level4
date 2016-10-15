package seedu.flexitrack.model.task;

import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;



import seedu.flexitrack.commons.exceptions.IllegalValueException;

public class DateTimeInfoParser {
    public String timingInfo;

    public DateTimeInfoParser(String givenTime) throws IllegalValueException {
        Parser parser = new Parser(); 
        List<DateGroup> dateParser = parser.parse(givenTime);
        if (!isValidDateTimeInfo(dateParser)) {
            throw new IllegalValueException("error");
            //TODO: use MESSAGE_DATETIMEINFO_CONSTRAINTS instead of error
        }       
        this.timingInfo = dateParser.get(0).getDates().toString();
        timingInfo = timingInfo.substring(5, 20);
    }
    
    public String getParsedTimingInfo(){
        return timingInfo;
    }
    
    public static boolean isValidDateTimeInfo(List<DateGroup> test) {
        if (!test.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}