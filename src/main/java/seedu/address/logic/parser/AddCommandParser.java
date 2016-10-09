package seedu.address.logic.parser;

import com.joestelmach.natty.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;

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
            "add\\s+(?<"+REGEX_REF_TASK_NAME+">[^/]+?)(\\s+(" +
            "(s/(?<"+REGEX_REF_START_DATE+">[^/]+?)(?!.*\\ss/))|" +
            "(st/(?<"+REGEX_REF_START_TIME+">[^/]+?)(?!.*\\sst/))|" +
            "(e/(?<"+REGEX_REF_END_DATE+">[^/]+?)(?!.*\\se/))|" +
            "(et/(?<"+REGEX_REF_END_TIME+">[^/]+?)(?!.*\\set/))|" +
            "(l/(?<"+REGEX_REF_LOCATION+">[^/]+?)(?!.*\\sl/))|" +
            "(p/(?<"+REGEX_REF_PRIORITY_LEVEL+">[^/]+?)(?!.*\\sp/))|" +
            "(r/(?<"+REGEX_REF_RECURRING_TYPE+">[^/]+?)(?!.*\\sr/))|" +
            "(n/(?<"+REGEX_REF_NUMBER_OF_RECURRENCE+">[^/]+?)(?!.*\\sn/))|" +
            "(c/(?<"+REGEX_REF_CATEGORY+">[^/]+?)(?!.*\\sc/))|" +
            "(d/(?<"+REGEX_REF_DESCRIPTION+">[^/]+?)(?!.*\\sd/))" +
            ")(?=\\s|$)){0,10}", Pattern.CASE_INSENSITIVE);
    
    private Parser dateParser;
    
    public AddCommandParser() {
        this.dateParser = new Parser();
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
               String taskName = matcher.group(REGEX_REF_TASK_NAME);
               String startDate = matcher.group(REGEX_REF_START_DATE);
               String startTime = matcher.group(REGEX_REF_START_TIME);
               String endDate = matcher.group(REGEX_REF_END_DATE);
               String endTime = matcher.group(REGEX_REF_END_TIME);
               String location = matcher.group(REGEX_REF_LOCATION);
               String priority = matcher.group(REGEX_REF_PRIORITY_LEVEL);
               String recurringType = matcher.group(REGEX_REF_RECURRING_TYPE);
               String numRecurrence = matcher.group(REGEX_REF_NUMBER_OF_RECURRENCE);
               String category = matcher.group(REGEX_REF_CATEGORY);
               String description = matcher.group(REGEX_REF_DESCRIPTION);
               // TODO: Create AddCommand
        }
        
        throw new ParseException(commandText);
    }

}
