package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;

public class AddCommandParser extends CommandParser<AddCommand> {
    private static final String HEADER = "add";
    private static final String READABLE_FORMAT = "add TASK_NAME [s/START_DATE] [st/START_TIME] " +
            "[e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] " +
            "[n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]";
    private static final Pattern REGEX_FORMAT = Pattern.compile("add\\s+(?<TASK_NAME>[^/]+)\\s+" +
            "((s/(?<START_DATE>[^/]+)(?!.*\\ss/)\\s+)" +
            "|(st/(?<START_TIME>[^/]+)(?!.*\\sst/)\\s+)" +
            "){0,10}");
    
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
        return null;
    }

}
