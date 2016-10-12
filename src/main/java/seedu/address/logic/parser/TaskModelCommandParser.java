package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.DateParser.InferredDate;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.RecurrenceType;

/**
 * This class contains common parsing methods for parsing Task fields.
 * CommandParsers should extend this class if they need this facility.
 *
 * @param <T> A Command that this Parser is going to produce upon successful parsing
 */
public abstract class TaskModelCommandParser<T extends Command> extends CommandParser<T> {
    private DateParser dateParser;
    
    protected TaskModelCommandParser() {
        this.dateParser = new DateParser();
    }
    
    protected String parseTaskName(String taskNameText) throws ParseException {
        if (taskNameText == null)
            return null;
        return taskNameText.trim();
    }
    
    protected InferredDate parseStartDate(String dateText) throws ParseException {
        try {
            if (dateText == null)
                return null;
            dateText = dateText.trim();
            return dateParser.parseSingle(dateText);
        } catch (ParseException ex) {
            throw new ParseException(dateText, "START_DATE: " + ex.getFailureDetails());
        }
    }
    
    protected InferredDate parseEndDate(String dateText) throws ParseException {
        try {
            if (dateText == null)
                return null;
            dateText = dateText.trim();
            return dateParser.parseSingle(dateText);
        } catch (ParseException ex) {
            throw new ParseException(dateText, "END_DATE: " + ex.getFailureDetails());
        }
    }
    
    protected String parseLocation(String locationText) throws ParseException {
        if (locationText == null)
            return null;
        return locationText.trim();
    }
    
    protected PriorityLevel parsePriorityLevel(String priorityLevelText) throws ParseException {
        try {
            if (priorityLevelText == null)
                return null;
            priorityLevelText = priorityLevelText.trim();
            return PriorityLevel.valueOfIgnoreCase(priorityLevelText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(priorityLevelText, "PRIORITY_LEVEL: Unknown type '" + priorityLevelText + "'");
        }
    }
    
    protected RecurrenceType parseRecurrenceType(String recurrenceTypeText) throws ParseException {
        try {
            if (recurrenceTypeText == null)
                return null;
            recurrenceTypeText = recurrenceTypeText.trim()
            return RecurrenceType.valueOfIgnoreCase(recurrenceTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(recurrenceTypeText, "RECURRING_TYPE: Unknown type '" + recurrenceTypeText + "'");
        }
    }
    
    protected Integer parseNumberOfRecurrence(String numRecurrenceText) throws ParseException {
        if (numRecurrenceText == null)
            return null;
        
        int numRecurrence = 0;
        boolean parseError = false;
        
        try {
            numRecurrenceText = numRecurrenceText.trim()
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
