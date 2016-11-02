//@@author A0139916U
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.logic.commands.Command;
import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * This class contains common parsing methods for parsing Task fields.
 * Each of the parse method takes in a string which can be null, and return
 * the respective parsed object. 
 */
public abstract class TaskFieldParser<T extends Command> implements CommandParser<T> {
    /*
     * Provides standard regex ref names for various task fields that might be used by subclasses.
     */
    protected static final String REGEX_REF_TASK_NAME = "TaskName";
    protected static final String REGEX_REF_START_DATE = "StartDate";
    protected static final String REGEX_REF_END_DATE = "EndDate";
    protected static final String REGEX_REF_LOCATION = "Location";
    protected static final String REGEX_REF_PRIORITY_LEVEL = "Priority";
    protected static final String REGEX_REF_RECURRING_TYPE = "RecurringType";
    protected static final String REGEX_REF_NUMBER_OF_RECURRENCE = "RecurringNumber";
    protected static final String REGEX_REF_CATEGORY = "Category";
    protected static final String REGEX_REF_DESCRIPTION = "Description";
    
    protected final DateParser dateParser;
    
    protected TaskFieldParser() {
        this.dateParser = new DateParser();
    }
    
    protected String parseTaskName(String taskNameText) throws ParseException {
        if (taskNameText == null)
            return null;
        return taskNameText.trim();
    }
    
    protected InferredDate parseStartDate(String dateText) throws ParseException {
        return parseDate(dateText, "START_DATE: ");
    }
    
    protected InferredDate parseEndDate(String dateText) throws ParseException {
        return parseDate(dateText, "END_DATE: ");
    }
    
    private InferredDate parseDate(String dateText, String errorField) throws ParseException {
        if (dateText == null)
            return null;
        
        String trimmedDateText = dateText.trim();
        try {
            return dateParser.parseSingle(trimmedDateText);
        } catch (ParseException ex) {
            throw new ParseException(trimmedDateText, errorField + ex.getFailureDetails());
        }
    }
    
    protected String parseLocation(String locationText) throws ParseException {
        if (locationText == null)
            return null;
        return locationText.trim();
    }
    
    protected PriorityLevel parsePriorityLevel(String priorityLevelText) throws ParseException {
        if (priorityLevelText == null)
            return null;
        
        String trimmedPriorityLevelText = priorityLevelText.trim();
        try {
            return PriorityLevel.valueOfIgnoreCase(trimmedPriorityLevelText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(trimmedPriorityLevelText, "PRIORITY_LEVEL: Unknown type '" + priorityLevelText + "'");
        }
    }
    
    protected RecurrenceType parseRecurrenceType(String recurrenceTypeText) throws ParseException {
        if (recurrenceTypeText == null)
            return null;
        
        String trimmedRecurrenceTypeText = recurrenceTypeText.trim();
        try {
            return RecurrenceType.valueOfIgnoreCase(trimmedRecurrenceTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(trimmedRecurrenceTypeText, "RECURRING_TYPE: Unknown type '" + recurrenceTypeText + "'");
        }
    }
    
    protected Integer parseNumberOfRecurrence(String numRecurrenceText) throws ParseException {
        if (numRecurrenceText == null)
            return null;
        
        String trimmedNumRecurrenceText = numRecurrenceText.trim();
        int numRecurrence = 0;
        boolean parseError = false;
        
        try {
            numRecurrence = Integer.parseInt(trimmedNumRecurrenceText);
            if (numRecurrence < 0)
                parseError = true;
        } catch (NumberFormatException ex) {
            parseError = true;
        }
        
        if (parseError)
            throw new ParseException(trimmedNumRecurrenceText, "NUMBER_OF_RECURRENCE: Must be a nonnegative whole number!");
        
        return numRecurrence;
    }

    protected String parseCategory(String categoryText) throws ParseException {
        if (categoryText == null)
            return null;
        return categoryText.trim();
    }
    
    protected String parseDescription(String descriptionText) throws ParseException {
        if (descriptionText == null)
            return null;
        return descriptionText.trim();
    }
}
