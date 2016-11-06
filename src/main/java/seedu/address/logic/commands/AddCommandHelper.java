package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

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
    
    private static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided.";
    private static final String MESSAGE_END_DATE_CONSTRAINTS = "End date should be later than start date.";
    private static final String MESSAGE_DATE_CONSTRAINTS = "Invalid date.";
    
    /** used to check for invalid dates e.g 40 Oct */
    private static final String REGEX_VALIDATE_DATE = ".*?(?:SEEK > by_day (?<date>\\d+) \\W+).*";

    private static final String STRING_CONSTANT_ONE = "1";
    private static final int BASE_INDEX = 0;
    private static final int MAX_DAYS_IN_MONTH = 31;
    
    /**
     * Returns a HashMap containing values of taskName, startDate, endDate, recurrenceRate and priority.
     *
     * @param args  HashMap containing Optional<String> values of taskName, startDate, endDate, rate, timePeriod and priority.
     * @return      HashMap containing values of taskName, startDate, endDate, recurrenceRate and priority.
     * @throws IllegalValueException  If recurrenceRate or date is invalid.
     */
    public static HashMap<String, Object> convertStringToObjects(HashMap<String, Optional<String>> mapOfStrings) 
            throws IllegalValueException {
        Name taskName = new Name(mapOfStrings.get(Name.getMapNameKey()).get());
        Date startDate = convertStringToStartDate(mapOfStrings.get(DateTime.getMapStartDateKey()));
        Date endDate = convertStringToEndDate(mapOfStrings.get(DateTime.getMapEndDateKey()), startDate);
        RecurrenceRate recurrenceRate = convertStringToRecurrenceRate(mapOfStrings.get(RecurrenceRate.getMapRateKey()), 
                mapOfStrings.get(TimePeriod.getTimePeriodKey())); 
        
        if (isRecurWeekdaysButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.getTimePeriod().toString());
        } 
        
        Priority priority = Priority.convertStringToPriority(mapOfStrings.get(Priority.getMapPriorityKey()).get());
        
        checkInvalidCombinations(startDate, endDate, recurrenceRate);
        
        return mapContainingVariables(taskName, startDate, endDate, recurrenceRate, priority);
    }

    /**
     * Checks whether the combination of startDate, endDate and recurrenceRate is valid.
     *
     * @param startDate Start date of Task.
     * @param endDate   End date of Task.
     * @param recurrenceRate    Recurrence rate of Task.
     * @throws IllegalValueException  If invalid combination of startDate, endDate and recurrenceRate exists.
     */
    private static void checkInvalidCombinations(Date startDate, Date endDate, RecurrenceRate recurrenceRate)
            throws IllegalValueException {
        if (isOtherRecurrenceButDatesNotGiven(startDate, endDate, recurrenceRate)) {
            logger.log(Level.FINE, "IllegalValueException thrown in AddCommandHelper, recurrence rate given but dates not given");
            throw new IllegalValueException(MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }
        
        if (endDate != null && startDate != null && endDate.before(startDate)) {
            logger.log(Level.FINE, "IllegalValueException thrown in AddCommandHelper, end date is before start date");
            throw new IllegalValueException(MESSAGE_END_DATE_CONSTRAINTS);
        }
    }

    /**
     * Put the input parameters taskName, startDate, endDate, recurrenceRate and priority into a HashMap and returns the map.
     *
     * @param taskName          Name of Task.
     * @param startDate         Start date of Task.
     * @param endDate           End date of Task.
     * @param recurrenceRate    Recurrence rate of Task.
     * @param priority          Priority of Task.
     * @return                  HashMap containing values of taskName, startDate, endDate, recurrenceRate and priority.
     */
    private static HashMap<String, Object> mapContainingVariables(Name taskName, Date startDate, Date endDate,
            RecurrenceRate recurrenceRate, Priority priority) {
        assert taskName != null && priority != null;
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put(Name.getMapNameKey(), taskName);
        map.put(DateTime.getMapStartDateKey(), startDate);
        map.put(DateTime.getMapEndDateKey(), endDate);
        map.put(RecurrenceRate.getMapRecurrenceRateKey(), recurrenceRate);
        map.put(Priority.getMapPriorityKey(), priority);
        
        return map;
    }
    
    /**
     * Converts given String into the Date representation.
     *
     * @param startDateString   User's input of date.
     * @return                  The Date representation of startDateString if startDateString is present. 
     *                          Else, returns null.
     * @throws IllegalValueException    If startDateString cannot be converted into a Date object.
     */
    private static Date convertStringToStartDate(Optional<String> startDateString) throws IllegalValueException {
        assert startDateString != null;
        Date startDate = null;
        
        if (startDateString.isPresent()) {
            validateDateString(startDateString.get());
            startDate = DateTime.convertStringToDate(startDateString.get());

            if (!DateTime.hasTimeValue(startDateString.get())) {
                startDate = DateTime.setTimeToStartOfDay(startDate);
            }
        }
        return startDate;
    }

    /**
     * Validates if dateString is valid.
     *
     * @param dateString   User's input of date.
     * @throws IllegalValueException    If dateString is an invalid date e.g 40 Oct.
     */
    private static void validateDateString(String dateString) throws IllegalValueException {
        if (DateTime.isValidDate(dateString)) {
            List<DateGroup> dates = new Parser().parse(dateString);
            String syntaxTree = dates.get(BASE_INDEX).getSyntaxTree().toStringTree();
            Pattern pattern = Pattern.compile(REGEX_VALIDATE_DATE);
            Matcher matcher = pattern.matcher(syntaxTree);
            if (matcher.matches() && Integer.parseInt(matcher.group("date")) > MAX_DAYS_IN_MONTH) {
                logger.log(Level.FINE, "IllegalValueException thrown in AddCommandHelper, validateDateString, invalid date");
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
        } else {
            logger.log(Level.FINE, "IllegalValueException thrown in AddCommandHelper, validateDateString, "
                    + "input does not conform to user guide");
            throw new IllegalValueException(DateTime.getMessageValueConstraints());
        }
    }
    
    /**
     * Converts given String into the Date representation. If user did not specify the date in endDateString,
     * it will take the date of startDate.
     *
     * @param endDateString User's input of date.
     * @param startDate     Start date of Task.
     * @return              The Date representation of endDateString if endDateString is present. Else, returns null.
     * @throws IllegalValueException    If endDateString cannot be converted into a Date object.
     */
    private static Date convertStringToEndDate(Optional<String> endDateString, Date startDate) throws IllegalValueException {
        assert endDateString != null;
        Date endDate = null;
        
        if (endDateString.isPresent()) {
            validateDateString(endDateString.get());
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
     * @param rateString        User's input of rate.
     * @param timePeriodString  User's input of time period.
     * @return                  The RecurrenceRate representation of rateString and timePeriodString if present. 
     *                          Else, returns null.
     * @throws IllegalValueException    If rateString is present but timePeriodString isn't present
     *                                  (for e.g, "3" is invalid. Examples such as "3 days" or "week" is valid).
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
            logger.log(Level.FINE, "IllegalValueException thrown in AddCommandHelper, convertStringToRecurrenceRate, "
                    + "rate is present but time period absent");
            throw new IllegalValueException(RecurrenceRate.getMessageValueConstraints());
        }
        return recurrenceRate;
    }
    
    /**
     * Returns true if both dates are null and the Task repeats every weekday e.g "monday". 
     *
     * @param startDate         Start date of Task.
     * @param endDate           End date of Task.
     * @param recurrenceRate    Recurrence rate of Task.
     * @return                  True if both dates are null and Task repeats every weekday. Else, returns false.
     */
    private static boolean isRecurWeekdaysButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        if (recurrenceRate != null) {
            TimePeriod recurrenceRateTimePeriod = recurrenceRate.getTimePeriod();
            TimePeriod day = TimePeriod.DAY;
            
            return recurrenceRateTimePeriod != day 
                    && recurrenceRate.getTimePeriod().toString().toLowerCase().contains(day.toString().toLowerCase()) 
                    && startDate == null && endDate == null;
        } else {
            return false;
        }
    }
    
    /**
     * Returns true if both dates are null and the Task repeats every non-weekday e.g "week". 
     *
     * @param startDate         Start date of Task.
     * @param endDate           End date of Task.
     * @param recurrenceRate    Recurrence rate of Task.
     * @return                  True if both dates are null and Task repeats every non-weekday. Else, returns false.
     */
    private static boolean isOtherRecurrenceButDatesNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && startDate == null && endDate == null;
    }
    
    public static String getMessageRecurDateTimeConstraints() {
        return MESSAGE_RECUR_DATE_TIME_CONSTRAINTS;
    }
    
    public static String getMessageEndDateConstraints() {
        return MESSAGE_END_DATE_CONSTRAINTS;
    }
    
    public static String getMessageDateConstraints() {
        return MESSAGE_DATE_CONSTRAINTS;
    }
}