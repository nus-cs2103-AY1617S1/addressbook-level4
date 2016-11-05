package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_ILLEGAL_DATE_INPUT;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.DateGroup;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.RecurringType;

//@@author A0135782Y
/**
 * Parses the recurring info to determine the recurring type of the input.
 * DateParser is a singleton, use getInstance() to gain access to this class.
 */
public class DateParser {
    private static DateParser instance;
    
    private HashSet<RecurringType> recurringTypes;
    private static final com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
    
    private DateParser () {
        populateSupportedRecurringTypes();
    }

    /**
     * Populate the recurringTypes with what is in RecurringType enum.
     */
    private void populateSupportedRecurringTypes() {
        recurringTypes = new HashSet<RecurringType>();
        for(RecurringType t : RecurringType.values()) {
            recurringTypes.add(t);
        }
        recurringTypes.remove(RecurringType.IGNORED);
    }
    
    /**
     * Obtain the recurring type using a string
     * 
     * @param input The recurring type in formatted String form.
     * @return The recurring type in enum form.
     * @throws IllegalArgumentException If the string is not the same as the RecurringType value
     */
    private RecurringType getRecurringType(String input) throws IllegalArgumentException {
        if (recurringTypes.contains(RecurringType.valueOf(input))) {
            return RecurringType.valueOf(input);
        }
        return RecurringType.IGNORED;
    }
    
    /**
     * Extracts the recurring type from a unformatted string.
     * Formats the string to extract out the recurring type.
     * 
     * @param recurringInfo The recurring type in unformatted string String form.
     * @return The recurring type in enum form.
     * @throws IllegalArgumentException If the string is not the same as the RecurringType value
     */
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
    
    /**
     * Obtains date group list for add XXXX from XXXX to XXXX
     * Helps to allow greater flexibility in the command.
     * 
     * @param dateInput The command for adding non floating task.
     * @return The date group list using natty.
     * @throws IllegalValueException If the input does not yield any date group.
     */
    public List<Date> getFromToDatesFromString(String dateInput) throws IllegalValueException {
        List<DateGroup> dateGroups = nattyParser.parse(dateInput);
        try {
            return dateGroups.get(0).getDates();
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_DATE_INPUT);
        }
    }
    
    /**
     * Helper method to obtain the recurring period in integer
     * 
     * @param input The recurring period in string 
     * @return The recurring period in integer.
     * @throws NumberFormatException If the Stirng cannot be converted to integer properly.
     */
    public int extractRecurringPeriod(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }
    
    public static DateParser getInstance() {
        if (instance == null )
            instance = new DateParser();
        return instance;
    }
}
