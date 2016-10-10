package seedu.address.logic.parser;

import java.util.regex.Pattern;

public class ModifyCommandParser extends CommandParser<ModifyCommand> {
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
            "modify\\s+(?<"+REGEX_REF_INDEX+">[^/]+)(\\s+(" +
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
            ")(?=\\s|$)){0,11}", Pattern.CASE_INSENSITIVE);

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
        // TODO Auto-generated method stub
        return null;
    }

}
