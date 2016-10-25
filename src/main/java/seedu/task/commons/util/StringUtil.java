package seedu.task.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.EventDuration;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
	/**
	 * DateTimeFormatter for LocalTimeDate fields. 
	 */
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
	private static final int DATE_INDEX = 0;
	
	/**
	 * Parse a String argument into date format. 
	 * @param parser
	 * @param dateArg
	 * @return date in LocalDateTime format
	 * @throws IllegalValueException
	 */
	public static LocalDateTime parseStringToTime(String dateArg) throws IllegalValueException {
		PrettyTimeParser parser = new PrettyTimeParser();
		
		//invalid start date
		if(dateArg == null) throw new IllegalValueException(EventDuration.MESSAGE_DURATION_CONSTRAINTS);
		
		List<Date> parsedResult = parser.parse(dateArg);
		
		//cannot parse
		if(parsedResult.isEmpty()) throw new IllegalValueException(EventDuration.MESSAGE_DURATION_CONSTRAINTS);
		
		return LocalDateTime.ofInstant(parsedResult.get(DATE_INDEX).toInstant(), ZoneId.systemDefault()); 
	}
	
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
}
