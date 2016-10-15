package seedu.ggist.logic.parser;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.ggist.commons.exceptions.IllegalValueException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DateTimeParser {

    private String date;
    private String time;
    
    public DateTimeParser(String raw) throws IllegalValueException {
        List<Date> dateTimeData = new PrettyTimeParser().parse(raw);
        if (dateTimeData.size() == 0) {
            throw new IllegalValueException("Invalid date or time format");
        }
        Date dateTime = dateTimeData.get(0);
        date = parseDate(dateTime);
        time = parseTime(dateTime);
    }
    
    private String parseTime(Date dateTime) throws IllegalValueException {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String time = sdf.format(dateTime).toString();
        String currentTime = sdf.format(new Date()).toString();
        if (currentTime.equals(time)) {
            throw new IllegalValueException("Seems like you missed out the time.");
        }
        return time;
    }
    
    private String parseDate(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy");
        return sdf.format(dateTime).toString();
    }
    
    public String getDate() {
        return date;
    }
    
    public String getTime() {
        return time;
    }
}
