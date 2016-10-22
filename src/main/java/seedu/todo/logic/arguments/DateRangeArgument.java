package seedu.todo.logic.arguments;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.commons.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

//@@author A0135817B
public class DateRangeArgument extends Argument<DateRange> {
    private static final PrettyTimeParser parser = new PrettyTimeParser();
    private static final TimeUtil timeUtil = new TimeUtil();
    
    // TODO: Review all error messages to check for user friendliness 
    private static final String TOO_MANY_DATES_FORMAT = "You specified too many time - we found: %s";
    private static final String NO_DATE_FOUND_FORMAT = "%s does not seem to contain a date";
    
    public DateRangeArgument(String name) {
        // Makes sure that there is a default value, so that callers won't get null when they getValue()
        super(name, new DateRange(null));
    }

    public DateRangeArgument(String name, DateRange defaultValue) {
        super(name, defaultValue);
    }
    
    @Override
    public void setValue(String input) throws IllegalValueException {
        super.setValue(input);
        
        if (StringUtil.isEmpty(input)) {
            return;
        }
        
        input = TimeUtil.toAmericanDateFormat(input);
        List<Date> dateGroups = parser.parse(input);
        
        List<LocalDateTime> dates = dateGroups.stream()
            .map(TimeUtil::asLocalDateTime)
            .sorted()
            .collect(Collectors.toList());

        if (dates.size() > 2) {
            tooManyDatesError(dates);
        } else if (dates.size() == 0) {
            throw new IllegalValueException(String.format(DateRangeArgument.NO_DATE_FOUND_FORMAT, input));
        }
        
        if (dates.size() == 1) {
            value = new DateRange(dates.get(0));
        } else {
            value = new DateRange(dates.get(0), dates.get(1));
        }
    }
    
    private void tooManyDatesError(List<LocalDateTime> dates) throws IllegalValueException {
        StringJoiner sj = new StringJoiner(", ");
        for (LocalDateTime d : dates) {
            sj.add(timeUtil.getTaskDeadlineText(d));
        }
        String message = String.format(DateRangeArgument.TOO_MANY_DATES_FORMAT, sj.toString());
        throw new IllegalValueException(message);
    }
    
}
