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
	
	//@@author A0144702N
	/**
	 * DateTimeFormatter for LocalTimeDate fields. 
	 */
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
	private static final int DATE_INDEX = 0;
	public static final String TIME_CONSTRAINTS = "No abbreviation is allowed for relative, ie: tmrw. \n"
			+ "But Fri, Mon, etc is okay.\n"
			+ "MM DD YY is the expected numerical sequence. \n"
			+ "Possible event duration could be:"
			+ "today 4pm /to tomorrow 4pm";
	private static final int SIMILIAR_THRESHOLD = 1; 
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
		if(dateArg == null) throw new IllegalValueException(TIME_CONSTRAINTS);
		
		List<Date> parsedResult = parser.parse(dateArg);
		
		//cannot parse
		if(parsedResult.isEmpty()) throw new IllegalValueException(TIME_CONSTRAINTS);
		
		return LocalDateTime.ofInstant(parsedResult.get(DATE_INDEX).toInstant(), ZoneId.systemDefault()); 
	}
	
	
    public static boolean containsIgnoreCase(String source, String query) {
        return source.toUpperCase().indexOf(query.toUpperCase()) != -1;
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

    
    public static boolean findMatch(String a, String b) {
    	
    	//short circuit if one of null
    	if(a == null || b == null) {
        	return false;
        }
        
    	//short circuit if contains
    	if(containsIgnoreCase(a, b)) {
    		return true;
    	}
    	
    	//short circuit if length differ by more than 3.
    	if(Math.abs(a.length()- b.length()) > SIMILIAR_THRESHOLD) {
    		return false;
    	}
    	
        return getDistance(a, b) <= SIMILIAR_THRESHOLD;
    }

    public static int getDistance(String a, String b) {
        
    	int aIndex = a.length()-1;
        int bIndex = b.length()-1;
        
        return levenshteinDistance(a.toUpperCase(), b.toUpperCase(), aIndex, bIndex, 0);
    }

    private static int levenshteinDistance(String a, String b, int aIndex, int bIndex, int currentCost) {
        //short circuit
    	if(aIndex < 0) return bIndex+1;
        if(bIndex < 0) return aIndex+1;
        if(currentCost > SIMILIAR_THRESHOLD) {
        	return currentCost;
        }
        
        int subCost = (a.charAt(aIndex) == b.charAt(bIndex)) ? 0 : 1;
        
        return min(
                levenshteinDistance(a, b, aIndex-1, bIndex, currentCost+1)+1,
                levenshteinDistance(a, b, aIndex, bIndex-1, currentCost+1)+1,
                levenshteinDistance(a, b, aIndex-1, bIndex-1, (currentCost+subCost))+subCost
                );
 
    }

    private static int min(int i, int j, int k) {
        int min = i;
        if(j<min) min =j;
        if(k<min) min = k;
        
        return min;
    }

}
