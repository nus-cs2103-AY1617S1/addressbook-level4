package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_ILLEGAL_DATE_INPUT;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.DateGroup;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.RecurringType;

//@@A0135782Y
/**
 * Parses the recurring info to determine the recurring type of the input
 * 
 */
public class DateParser {
    private static DateParser instance;
    
    private HashSet<RecurringType> recurringTypes;
    private static final com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
    
    private DateParser () {
        populateSupportedRecurringTypes();
    }

    private void populateSupportedRecurringTypes() {
        recurringTypes = new HashSet<RecurringType>();
        for(RecurringType t : RecurringType.values()) {
            recurringTypes.add(t);
        }
        recurringTypes.remove(RecurringType.IGNORED);
    }
    
    private RecurringType getRecurringType(String input) throws IllegalArgumentException {
        if (recurringTypes.contains(RecurringType.valueOf(input))) {
            return RecurringType.valueOf(input);
        }
        return RecurringType.IGNORED;
    }
    
    public RecurringType extractRecurringInfo(String recurringInfo) throws IllegalArgumentException {
        recurringInfo = recurringInfo.toUpperCase().trim();
        return getRecurringType(recurringInfo);
    }
    
    /**
     * Parses through the dateInput and provides the Date from that input
     * 
     * @param dateInput
     *            The date that we want to convert from string to Date
     * @return A single Date from the string
     * @throws IllegalValueException 
     */
    public Date getDateFromString(String dateInput) throws IllegalValueException {
        List<DateGroup> dateGroups = nattyParser.parse(dateInput);
        try {
            return dateGroups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_DATE_INPUT);
        }
    }
    
    public List<Date> getFromToDatesFromString(String dateInput) throws IllegalValueException {
        List<DateGroup> dateGroups = nattyParser.parse(dateInput);
        try {
            return dateGroups.get(0).getDates();
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_DATE_INPUT);
        }
    }
    
    public static DateParser getInstance() {
        if (instance == null )
            instance = new DateParser();
        return instance;
    }

    public int extractRecurringPeriod(String group) throws NumberFormatException {
        return Integer.parseInt(group);
    }
}
