//@@author A0139916U
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * This class contains common parsing methods for parsing Task fields.
 */
public class TaskFieldParser {
    protected final DateParser dateParser;
    
    public TaskFieldParser() {
        this.dateParser = new DateParser();
    }
    
    public String parseTaskName(String taskNameText) throws ParseException {
        if (taskNameText == null)
            return null;
        return taskNameText.trim();
    }
    
    public InferredDate parseStartDate(String dateText) throws ParseException {
        return parseDate(dateText, "START_DATE: ");
    }
    
    public InferredDate parseEndDate(String dateText) throws ParseException {
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
    
    public String parseLocation(String locationText) throws ParseException {
        if (locationText == null)
            return null;
        return locationText.trim();
    }
    
    public PriorityLevel parsePriorityLevel(String priorityLevelText) throws ParseException {
        if (priorityLevelText == null)
            return null;
        
        String trimmedPriorityLevelText = priorityLevelText.trim();
        try {
            return PriorityLevel.valueOfIgnoreCase(trimmedPriorityLevelText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(trimmedPriorityLevelText, "PRIORITY_LEVEL: Unknown type '" + priorityLevelText + "'");
        }
    }
    
    public RecurrenceType parseRecurrenceType(String recurrenceTypeText) throws ParseException {
        if (recurrenceTypeText == null)
            return null;
        
        String trimmedRecurrenceTypeText = recurrenceTypeText.trim();
        try {
            return RecurrenceType.valueOfIgnoreCase(trimmedRecurrenceTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(trimmedRecurrenceTypeText, "RECURRING_TYPE: Unknown type '" + recurrenceTypeText + "'");
        }
    }
    
    public Integer parseNumberOfRecurrence(String numRecurrenceText) throws ParseException {
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

    public String parseCategory(String categoryText) throws ParseException {
        if (categoryText == null)
            return null;
        return categoryText.trim();
    }
    
    public String parseDescription(String descriptionText) throws ParseException {
        if (descriptionText == null)
            return null;
        return descriptionText.trim();
    }
}
