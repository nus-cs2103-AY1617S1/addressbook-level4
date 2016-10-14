package seedu.savvytasker.logic.parser;

import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * This class contains common parsing methods for parsing Task fields.
 */
public class TaskFieldParser {
    private DateParser dateParser;
    
    public TaskFieldParser() {
        this.dateParser = new DateParser();
    }
    
    public String parseTaskName(String taskNameText) throws ParseException {
        if (taskNameText == null)
            return null;
        return taskNameText.trim();
    }
    
    public InferredDate parseStartDate(String dateText) throws ParseException {
        try {
            if (dateText == null)
                return null;
            dateText = dateText.trim();
            return dateParser.parseSingle(dateText);
        } catch (ParseException ex) {
            throw new ParseException(dateText, "START_DATE: " + ex.getFailureDetails());
        }
    }
    
    public InferredDate parseEndDate(String dateText) throws ParseException {
        try {
            if (dateText == null)
                return null;
            dateText = dateText.trim();
            return dateParser.parseSingle(dateText);
        } catch (ParseException ex) {
            throw new ParseException(dateText, "END_DATE: " + ex.getFailureDetails());
        }
    }
    
    public String parseLocation(String locationText) throws ParseException {
        if (locationText == null)
            return null;
        return locationText.trim();
    }
    
    public PriorityLevel parsePriorityLevel(String priorityLevelText) throws ParseException {
        try {
            if (priorityLevelText == null)
                return null;
            priorityLevelText = priorityLevelText.trim();
            return PriorityLevel.valueOfIgnoreCase(priorityLevelText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(priorityLevelText, "PRIORITY_LEVEL: Unknown type '" + priorityLevelText + "'");
        }
    }
    
    public RecurrenceType parseRecurrenceType(String recurrenceTypeText) throws ParseException {
        try {
            if (recurrenceTypeText == null)
                return null;
            recurrenceTypeText = recurrenceTypeText.trim();
            return RecurrenceType.valueOfIgnoreCase(recurrenceTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(recurrenceTypeText, "RECURRING_TYPE: Unknown type '" + recurrenceTypeText + "'");
        }
    }
    
    public Integer parseNumberOfRecurrence(String numRecurrenceText) throws ParseException {
        if (numRecurrenceText == null)
            return null;
        
        int numRecurrence = 0;
        boolean parseError = false;
        
        try {
            numRecurrenceText = numRecurrenceText.trim();
            numRecurrence = Integer.parseInt(numRecurrenceText);
            if (numRecurrence < 0)
                parseError = true;
        } catch (NumberFormatException ex) {
            parseError = true;
        }
        
        if (parseError)
            throw new ParseException(numRecurrenceText, "NUMBER_OF_RECURRENCE: Must be a nonnegative whole number!");
        
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
