package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.DateParser.InferredDate;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.RecurrenceType;

public class AddCommandParser extends CommandParser<AddCommand> {
    private static final String HEADER = "add";
    private static final String READABLE_FORMAT = "add TASK_NAME [s/START_DATE] [st/START_TIME] " +
            "[e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] " +
            "[n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]";
    
    private static final String REGEX_REF_TASK_NAME = "TaskName";
    private static final String REGEX_REF_START_DATE = "StartDate";
    private static final String REGEX_REF_START_TIME = "StartTime";
    private static final String REGEX_REF_END_DATE = "EndDate";
    private static final String REGEX_REF_END_TIME = "EndTime";
    private static final String REGEX_REF_LOCATION = "Location";
    private static final String REGEX_REF_PRIORITY_LEVEL = "Priority";
    private static final String REGEX_REF_RECURRING_TYPE = "RecurringType";
    private static final String REGEX_REF_NUMBER_OF_RECURRENCE = "RecurringNumber";
    private static final String REGEX_REF_CATEGORY = "Category";
    private static final String REGEX_REF_DESCRIPTION = "Description";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            "add\\s+(?<"+REGEX_REF_TASK_NAME+">[^/]+)(\\s+(" +
            "(s/(?<"+REGEX_REF_START_DATE+">[^/]+)(?!.*\\ss/))|" +
            "(st/(?<"+REGEX_REF_START_TIME+">[^/]+)(?!.*\\sst/))|" +
            "(e/(?<"+REGEX_REF_END_DATE+">[^/]+)(?!.*\\se/))|" +
            "(et/(?<"+REGEX_REF_END_TIME+">[^/]+)(?!.*\\set/))|" +
            "(l/(?<"+REGEX_REF_LOCATION+">[^/]+)(?!.*\\sl/))|" +
            "(p/(?<"+REGEX_REF_PRIORITY_LEVEL+">[^/]+)(?!.*\\sp/))|" +
            "(r/(?<"+REGEX_REF_RECURRING_TYPE+">[^/]+)(?!.*\\sr/))|" +
            "(n/(?<"+REGEX_REF_NUMBER_OF_RECURRENCE+">[^/]+)(?!.*\\sn/))|" +
            "(c/(?<"+REGEX_REF_CATEGORY+">[^/]+)(?!.*\\sc/))|" +
            "(d/(?<"+REGEX_REF_DESCRIPTION+">[^/]+)(?!.*\\sd/))" +
            ")(?=\\s|$)){0,10}", Pattern.CASE_INSENSITIVE);
    
    private DateParser dateParser;
    
    public AddCommandParser() {
        this.dateParser = new DateParser();
    }
    
    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected AddCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            /* Pending changes to startTime and endTime */
            //String startTime = matcher.group(REGEX_REF_START_TIME);
            //String endTime = matcher.group(REGEX_REF_END_TIME);
            
            InferredDate startDate = parseStartDate(commandText, matcher.group(REGEX_REF_START_DATE));
            InferredDate endDate = parseEndDate(commandText, matcher.group(REGEX_REF_END_DATE));
            parseTaskName(commandText, matcher.group(REGEX_REF_TASK_NAME));
            parseLocation(commandText, matcher.group(REGEX_REF_LOCATION));
            parsePriorityLevel(commandText, matcher.group(REGEX_REF_PRIORITY_LEVEL));
            parseRecurrenceType(commandText, matcher.group(REGEX_REF_RECURRING_TYPE));
            parseNumberOfRecurrence(commandText, matcher.group(REGEX_REF_NUMBER_OF_RECURRENCE));
            parseCategory(commandText, matcher.group(REGEX_REF_CATEGORY));
            parseDescription(commandText, matcher.group(REGEX_REF_DESCRIPTION));
            
               
            // TODO: Create AddCommand here (require integration)
        }
        
        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

    private String parseTaskName(String commandText, String taskNameText) throws ParseException {
        return taskNameText;
    }
    
    private InferredDate parseStartDate(String commandText, String dateText) throws ParseException {
        try {
            if (dateText == null)
                return null;
            return dateParser.parseSingle(dateText);
        } catch (ParseException ex) {
            throw new ParseException(commandText, "START_DATE: " + ex.getFailureDetails());
        }
    }
    
    private InferredDate parseEndDate(String commandText, String dateText) throws ParseException {
        try {
            if (dateText == null)
                return null;
            return dateParser.parseSingle(dateText);
        } catch (ParseException ex) {
            throw new ParseException(commandText, "END_DATE: " + ex.getFailureDetails());
        }
    }
    
    private String parseLocation(String commandText, String locationText) throws ParseException {
        return locationText;
    }
    
    private PriorityLevel parsePriorityLevel(String commandText, String priorityLevelText) throws ParseException {
        try {
            if (priorityLevelText == null)
                return null;
            return PriorityLevel.valueOfIgnoreCase(priorityLevelText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(commandText, "PRIORITY_LEVEL: Unknown type '" + priorityLevelText + "'");
        }
    }
    
    private RecurrenceType parseRecurrenceType(String commandText, String recurrenceTypeText) throws ParseException {
        try {
            if (recurrenceTypeText == null)
                return null;
            return RecurrenceType.valueOfIgnoreCase(recurrenceTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(commandText, "RECURRING_TYPE: Unknown type '" + recurrenceTypeText + "'");
        }
    }
    
    private Integer parseNumberOfRecurrence(String commandText, String numRecurrenceText) throws ParseException {
        if (numRecurrenceText == null)
            return null;
        
        int numRecurrence = 0;
        boolean parseError = false;
        
        try {
            numRecurrence = Integer.parseInt(numRecurrenceText);
            if (numRecurrence < 0)
                parseError = true;
        } catch (NumberFormatException ex) {
            parseError = true;
        }
        
        if (parseError)
            throw new ParseException(commandText, "NUMBER_OF_RECURRENCE: Must be a nonnegative whole number!");
        
        return numRecurrence;
    }

    private String parseCategory(String commandText, String categoryText) throws ParseException {
        return categoryText;
    }
    
    private String parseDescription(String commandText, String descriptionText) throws ParseException {
        return descriptionText;
    }

}
