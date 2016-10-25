package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.TimePeriod;

public class AddCommandHelper {
    
    private static final String STRING_CONSTANT_ONE = "1";
    public static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided";
    
    public static HashMap<String, Object> convertStringToObjects(HashMap<String, Optional<String>> map) throws IllegalValueException {
        
        Name taskName = new Name(map.get("taskName").get());
        Date startDate = generateStartDateIfPresent(map.get("startDate"));
        Date endDate = generateEndDateIfPresent(map.get("endDate"), startDate);
        RecurrenceRate recurrenceRate = generateRecurrenceRateIfPresent(map.get("rate"), map.get("timePeriod")); 
        Priority priority = Priority.convertStringToPriority(map.get("priority").get());
        
        if (recurWeekdaysButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        } else if (otherRecurrenceButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            throw new IllegalValueException(MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }
        
        return mapContainingVariables(taskName, startDate, endDate, recurrenceRate, priority);
        
    }

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
    
    private static Date generateStartDateIfPresent(Optional<String> startDateString) {
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
    
    private static Date generateEndDateIfPresent(Optional<String> endDateString, Date startDate) {
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
    
    private static RecurrenceRate generateRecurrenceRateIfPresent(Optional<String> rateString,
            Optional<String> timePeriodString) throws IllegalValueException {
        assert rateString != null && timePeriodString != null;
        RecurrenceRate recurrenceRate = null;
        
        if (rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(rateString.get(), timePeriodString.get());
        } else if (!rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString.get());
        } else if (rateString.isPresent() && !timePeriodString.isPresent()) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
        return recurrenceRate;
    }
    
    private static boolean recurWeekdaysButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null;
    }

    private static boolean otherRecurrenceButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && startDate == null && endDate == null;
    }
}
