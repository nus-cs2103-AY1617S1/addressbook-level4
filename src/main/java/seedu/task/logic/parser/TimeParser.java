package seedu.task.logic.parser;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import org.antlr.runtime.tree.Tree;

import java.util.Date;
import java.util.List;
/**
 * @@author A0152958R
 * TimeParserResult hold the result after parse time with Natty.
 * TimeParserResult also check the time and update it according to the rule.
 */
public class TimeParser {

    private static final String NATTY_DATE_TIME = "DATE_TIME";
    private static final String NATTY_RELATIVE_DATE = "RELATIVE_DATE";
    private static final String NATTY_EXPLICIT_DATE = "EXPLICIT_DATE";
    private static final String NATTY_RELATIVE_TIME = "RELATIVE_TIME";
    private static final String NATTY_EXPLICIT_TIME = "EXPLICIT_TIME";
    private static final int NO_DATE_TIME = 0;
    private static final int ONE_DATE_TIME = 1;
    private static final int TWO_DATE_TIME = 2;
    private static final int THREE_DATE_TIME = 3;
    private static final int FIRST_DATE_INDEX = 0;
    private static final int SECOND_DATE_INDEX = 1;
    private static final int THIRD_DATE_INDEX = 2;

    private static Parser timeParser = new Parser();
    private TimeParserResult timeParserResult = new TimeParserResult();
    private int dateTimeCount;

    /**
     * Parse time string with Natty and return a TimeParserResult object
     */
    public TimeParserResult parseTime(String input) {

        if (input == null) {
            return timeParserResult;
        }
        List<DateGroup> groups = timeParser.parse(input);
        for (DateGroup group : groups){
            if (dateTimeCount > NO_DATE_TIME) {
                break;
            }
            List<Date> dates = group.getDates();
            Tree tree = group.getSyntaxTree();
            postTraverseSyntaxTree(tree, dates);
            timeParserResult.setMatchString(group.getText());
        }
        timeParserResult.updateDateTime();
        timeParserResult.checkInvalidTimeRange();
        if(!timeParserResult.isTimeValid()){
        	return null;
        }
        return timeParserResult;
    }

    /**
     * Recursively post traverse the syntax tree.
     * Retrieve the recognized date and time and its position in the syntax tree.
     * It's used to figure a date/time as start date/time or end date/time.
     * @param node the current traversed tree node
     * @param dates the recognized date list
     */
    private void postTraverseSyntaxTree(Tree node, List<Date> dates) {
        if (node.getText().equals(NATTY_DATE_TIME)){
            dateTimeCount++;
        }
        if (node.getText().equals(NATTY_RELATIVE_DATE) || node.getText().equals(NATTY_EXPLICIT_DATE)) {
            if (dateTimeCount == ONE_DATE_TIME) {
                timeParserResult.setFirstDate(dates.get(FIRST_DATE_INDEX));
            } else if (dateTimeCount == TWO_DATE_TIME) {
                timeParserResult.setSecondDate(dates.get(SECOND_DATE_INDEX));
            }else if (dateTimeCount == THREE_DATE_TIME) {
                timeParserResult.setThirdDate(dates.get(THIRD_DATE_INDEX));
            }
        } else if (node.getText().equals(NATTY_RELATIVE_TIME) || node.getText().equals(NATTY_EXPLICIT_TIME)) {
            if (dateTimeCount == ONE_DATE_TIME) {
                timeParserResult.setFirstTime(dates.get(FIRST_DATE_INDEX));
            } else if (dateTimeCount == TWO_DATE_TIME) {
                timeParserResult.setSecondTime(dates.get(SECOND_DATE_INDEX));
            }else if (dateTimeCount == THREE_DATE_TIME) {
                timeParserResult.setThirdTime(dates.get(THIRD_DATE_INDEX));
            }
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            postTraverseSyntaxTree(node.getChild(i), dates);
        }
    }
}
