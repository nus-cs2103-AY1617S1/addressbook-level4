package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.TimePeriod;

//@@author A0139655U
/**
 * Helps AddCommand to validate user input.
 */
public class AddCommandHelper {
    
    private final static Logger logger = LogsCenter.getLogger(AddCommandHelper.class);
    
    private static final String STRING_CONSTANT_ONE = "1";
    public static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided";
    
    /**
     * Returns a HashMap containing values of taskName, startDate, endDate, recurrenceRate and priority.
     *
     * @param args  HashMap containing Optional<String> values of taskName, startDate, endDate, rate, timePeriod and priority.
     * @return  HashMap containing values of taskName, startDate, endDate, recurrenceRate and priority.
     * @throws IllegalValueException  if recurrenceRate or date is invalid
     */
    public static HashMap<String, Object> convertStringToObjects(HashMap<String, Optional<String>> map) 
            throws IllegalValueException {
        Name taskName = new Name(map.get("taskName").get());
        Date startDate = convertStringToStartDate(map.get("startDate"));
        Date endDate = convertStringToEndDate(map.get("endDate"), startDate);
        RecurrenceRate recurrenceRate = convertStringToRecurrenceRate(map.get("rate"), map.get("timePeriod")); 
        Priority priority = Priority.convertStringToPriority(map.get("priority").get());
        
        if (isRecurWeekdaysButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        } else if (isOtherRecurrenceButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            logger.log(Level.FINE, "IllegalValueException caught in AddCommandHelper, recurrence rate given but dates not given");
            throw new IllegalValueException(MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }
        
        return mapContainingVariables(taskName, startDate, endDate, recurrenceRate, priority);
    }

    /**
     * Put the input parameters taskName, startDate, endDate, recurrenceRate and priority into a HashMap and returns the map.
     *
     * @param taskName
     * @param startDate
     * @param endDate
     * @param recurrenceRate
     * @param priority
     * @return  HashMap containing values of taskName, startDate, endDate, recurrenceRate and priority.
     */
    private static HashMap<String, Object> mapContainingVariables(Name taskName, Date startDate, Date endDate,
            RecurrenceRate recurrenceRate, Priority priority) {
        assert taskName != null && priority != null;
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("taskName", taskName);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("recurrenceRate", recurrenceRate);
        map.put("priority", priority);
        
        return map;
    }
    
    /**
     * Converts given String into the Date representation.
     *
     * @param startDateString
     * @return  the Date representation of startDateString if startDateString is present. Else, returns null.
     * @throws IllegalValueException if startDateString cannot be converted into a Date object
     */
    private static Date convertStringToStartDate(Optional<String> startDateString) throws IllegalValueException {
        assert startDateString != null;
        Date startDate = null;
        
        if (startDateString.isPresent()) {
            startDate = DateTime.convertStringToDate(startDateString.get());
            if (!DateTime.hasTimeValue(startDateString.get())) {
                startDate = DateTime.setTimeToStartOfDay(startDate);
            }
        }
        return startDate;
    }
    
    /**
     * Converts given String into the Date representation. If user did not specify the date in endDateString,
     * it will take the date of startDate.
     *
     * @param endDateString
     * @param startDate
     * @return  the Date representation of endDateString if endDateString is present. Else, returns null.
     * @throws IllegalValueException if endDateString cannot be converted into a Date object
     */
    private static Date convertStringToEndDate(Optional<String> endDateString, Date startDate) throws IllegalValueException {
        assert endDateString != null;
        Date endDate = null;
        
        if (endDateString.isPresent()) {
            endDate = DateTime.convertStringToDate(endDateString.get());
            if (startDate != null && !DateTime.hasDateValue(endDateString.get())) {
                endDate = DateTime.setEndDateToStartDate(startDate, endDate);
            }
            if (!DateTime.hasTimeValue(endDateString.get())) {
                endDate = DateTime.setTimeToEndOfDay(endDate);
            }
        }
        return endDate;
    }
    
    /**
     * Converts given String into the RecurrenceRate representation. 
     *
     * @param rateString
     * @param timePeriodString
     * @return  the RecurrenceRate representation of rateString and timePeriodString if present. Else, returns null.
     * @throws IllegalValueException    if rateString is present but timePeriodString isn't present
     * (for e.g, "3" is invalid. Examples such as "3 days" or "week" is valid).
     */
    private static RecurrenceRate convertStringToRecurrenceRate(Optional<String> rateString,
            Optional<String> timePeriodString) throws IllegalValueException {
        assert rateString != null && timePeriodString != null;
        RecurrenceRate recurrenceRate = null;
        
        if (rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(rateString.get(), timePeriodString.get());
        } else if (!rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString.get());
        } else if (rateString.isPresent() && !timePeriodString.isPresent()) {
            logger.log(Level.FINE, "IllegalValueException caught in AddCommandHelper, convertStringToRecurrenceRate()");
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
        return recurrenceRate;
    }
    
    /**
     * Returns true if both dates are null and the Task repeats every weekday e.g "monday". 
     *
     * @param startDate
     * @param endDate
     * @param recurrenceRate
     * @return  true if both dates are null and Task repeats every weekday. Else, returns false.
     */
    private static boolean isRecurWeekdaysButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null;
    }
    
    /**
     * Returns true if both dates are null and the Task repeats every non-weekday e.g "week". 
     *
     * @param startDate
     * @param endDate
     * @param recurrenceRate
     * @return  true if both dates are null and Task repeats every non-weekday. Else, returns false.
     */
    private static boolean isOtherRecurrenceButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && startDate == null && endDate == null;
    }
}