package seedu.address.logic.parser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ModifyCommand;
import seedu.address.logic.commands.models.ModifyCommandModel;
import seedu.address.logic.parser.DateParser.InferredDate;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.RecurrenceType;

public class ModifyCommandParser extends TaskModelCommandParser<ModifyCommand> {
    private static final String HEADER = "modify";
    private static final String READABLE_FORMAT = "modify INDEX [t/TASK_NAME] [s/START_DATE] "
            + "[st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] "
            + "[r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]";

    private static final String REGEX_REF_INDEX = "Index";
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
            "modify\\s+(?<"+REGEX_REF_INDEX+">([^/]+?(\\s+|$))+)((?<=\\s)(" +
            "(t/(?<"+REGEX_REF_TASK_NAME+">[^/]+)(?!.*\\st/))|" +
            "(s/(?<"+REGEX_REF_START_DATE+">[^/]+)(?!.*\\ss/))|" +
            "(st/(?<"+REGEX_REF_START_TIME+">[^/]+)(?!.*\\sst/))|" +
            "(e/(?<"+REGEX_REF_END_DATE+">[^/]+)(?!.*\\se/))|" +
            "(et/(?<"+REGEX_REF_END_TIME+">[^/]+)(?!.*\\set/))|" +
            "(l/(?<"+REGEX_REF_LOCATION+">[^/]+)(?!.*\\sl/))|" +
            "(p/(?<"+REGEX_REF_PRIORITY_LEVEL+">[^/]+)(?!.*\\sp/))|" +
            "(r/(?<"+REGEX_REF_RECURRING_TYPE+">[^/]+)(?!.*\\sr/))|" +
            "(n/(?<"+REGEX_REF_NUMBER_OF_RECURRENCE+">[^/]+?)(?!.*\\sn/))|" +
            "(c/(?<"+REGEX_REF_CATEGORY+">[^/]+)(?!.*\\sc/))|" +
            "(d/(?<"+REGEX_REF_DESCRIPTION+">[^/]+)(?!.*\\sd/))" +
            ")(\\s|$)){0,11}", Pattern.CASE_INSENSITIVE);

    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected ModifyCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {

            int index = parseIndex(matcher.group(REGEX_REF_INDEX).trim());
            InferredDate startDate = parseStartDate(matcher.group(REGEX_REF_START_DATE).trim());
            InferredDate endDate = parseEndDate(matcher.group(REGEX_REF_END_DATE).trim());
            String taskName = parseTaskName(matcher.group(REGEX_REF_TASK_NAME).trim());
            String location = parseLocation(matcher.group(REGEX_REF_LOCATION).trim());
            PriorityLevel priority = parsePriorityLevel(matcher.group(REGEX_REF_PRIORITY_LEVEL).trim());
            RecurrenceType recurrence = parseRecurrenceType(matcher.group(REGEX_REF_RECURRING_TYPE).trim());
            Integer nrOfRecurrence = parseNumberOfRecurrence(matcher.group(REGEX_REF_NUMBER_OF_RECURRENCE).trim());
            String category = parseCategory(matcher.group(REGEX_REF_CATEGORY).trim());
            String description = parseDescription(matcher.group(REGEX_REF_DESCRIPTION).trim());
            
               
            // TODO: Create ModifyCommand here (require integration)
            Date startDateTime = null, endDateTime = null;
            int numberOfRecurrence = 0;
            if (nrOfRecurrence != null) numberOfRecurrence = nrOfRecurrence.intValue();
            else numberOfRecurrence = ModifyCommandModel.UNINITIALIZED_NR_RECURRENCE_VALUE;
            if (startDate != null) startDateTime = startDate.getInferredDateTime();
            if (endDate != null) endDateTime = endDate.getInferredDateTime();
            return new ModifyCommand(
                    new ModifyCommandModel(index, taskName, startDateTime, 
                            endDateTime, location, priority, 
                            recurrence, numberOfRecurrence, 
                            category, description));
        }
        
        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

    private int parseIndex(String indexText) throws ParseException {
        boolean parseError = false;
        
        int index = 0;
        try {
            index = Integer.parseInt(indexText);
            
            if (index < 0)
                parseError = true;
        } catch (NumberFormatException ex) {
            parseError = true;
        }
        
        if (parseError)
            throw new ParseException(indexText, "INDEX: Must be a nonnegative whole number.");
            
        return index;
    }
}
