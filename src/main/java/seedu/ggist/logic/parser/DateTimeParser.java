package seedu.ggist.logic.parser;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.TaskTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//@@author A0138411N
public class DateTimeParser {

    private Date dateTime;
    private String date;
    private String time;
    
    /**
     * Parse the data and time input to a Date object
     * @param String
     * @throws IllegalValueException 
     */
    public DateTimeParser(String raw) throws IllegalValueException {
        List<Date> dateTimeData = new PrettyTimeParser().parse(raw);
        if (dateTimeData.size() == 0) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_TIME_VALUE);
        }
        dateTime = dateTimeData.get(0);
    }
    
    /**
     * Extracts time from Date object and convert to String
     * @param Date
     * @throws IllegalValueException
     */
    private void parseTime(Date dateTime) throws IllegalValueException {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        time = sdf.format(dateTime).toString();
        String currentTime = sdf.format(new Date()).toString();
        if (currentTime.equals(time)) {
            time = "";
        }
    }
    /**
     * Extracts date from Date object and convert to String
     * @param Date
     * @throws IllegalValueException
     */
    private void parseDate(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy");
        date = sdf.format(dateTime).toString();
    }
    
    public String getDate() {
        parseDate(dateTime);
        return date;
    }
    
    public String getTime() throws IllegalValueException {
        parseTime(dateTime);
        return time;
    }
    
    public Date getDateTime() {
        return dateTime;
    }
}
