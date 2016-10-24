package seedu.oneline.logic.parser;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.task.TaskTime;

public class DateParser {
    public DateParser(){ }
    
    /**
     * Returns the date represented by input time string
     * 
     * @param time the user given time string
     * @return the date represented by the time string
     * @throws IllegalValueException if given time string is invalid
     * 
     */
    @SuppressWarnings("deprecation")
    public static Date parseDate(String time) throws IllegalValueException{
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser(); // use the natty parser
        List<DateGroup> dates = parser.parse(time);

        if (!isValidTaskTime(dates)) {
            throw new IllegalValueException(TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        }
        
        Date date = dates.get(0).getDates().get(0);
        
        // if time was not explicitly declared, set the time to 2359
        if (dates.get(0).isTimeInferred()){
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
        }
        
        return date;
    }

    /**
     * Returns true if the time supplied is valid by checking the result of parser.parse
     * 
     * @param test the list of dategroups under test
     * @return true if list contains a valid date
     * 
     * Pre-condition: test is the return value of applying natty's Parser.parse(time) 
     * where time is the time in question
     */
    private static boolean isValidTaskTime(List<DateGroup> test) {
        return !(test.isEmpty() || test.get(0).getDates().isEmpty());
    }
}
