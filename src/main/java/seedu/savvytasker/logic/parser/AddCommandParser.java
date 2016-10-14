package seedu.savvytasker.logic.parser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.logic.commands.AddCommand;
import seedu.savvytasker.logic.commands.models.AddCommandModel;
import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

public class AddCommandParser extends TaskModelCommandParser<AddCommand> {
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
            "add\\s+(?<"+REGEX_REF_TASK_NAME+">([^/]+?(\\s+|$))+)((?<=\\s)(" +
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
            ")(\\s|$)){0,10}", Pattern.CASE_INSENSITIVE);
    
    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    public AddCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            /* Pending changes to startTime and endTime */
            //String startTime = matcher.group(REGEX_REF_START_TIME);
            //String endTime = matcher.group(REGEX_REF_END_TIME);

            InferredDate startDate = parseStartDate(matcher.group(REGEX_REF_START_DATE));
            InferredDate endDate = parseEndDate(matcher.group(REGEX_REF_END_DATE));
            String taskName = parseTaskName(matcher.group(REGEX_REF_TASK_NAME));
            String location = parseLocation(matcher.group(REGEX_REF_LOCATION));
            PriorityLevel priority = parsePriorityLevel(matcher.group(REGEX_REF_PRIORITY_LEVEL));
            RecurrenceType recurrence = parseRecurrenceType(matcher.group(REGEX_REF_RECURRING_TYPE));
            Integer nrOfRecurrence = parseNumberOfRecurrence(matcher.group(REGEX_REF_NUMBER_OF_RECURRENCE));
            String category = parseCategory(matcher.group(REGEX_REF_CATEGORY));
            String description = parseDescription(matcher.group(REGEX_REF_DESCRIPTION));
            
            // TODO: Create AddCommand here (require integration)
            Date startDateTime = null, endDateTime = null;
            int numberOfRecurrence = 0;
            if (nrOfRecurrence != null) numberOfRecurrence = nrOfRecurrence.intValue();
            if (startDate != null) startDateTime = startDate.getInferredDateTime();
            if (endDate != null) endDateTime = endDate.getInferredDateTime();
            return new AddCommand(
                    new AddCommandModel(taskName, startDateTime, 
                            endDateTime, location, priority, 
                            recurrence, numberOfRecurrence, 
                            category, description));
        }
        
        throw new ParseException(commandText, String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, getRequiredFormat()));
    }

}
