package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;


/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    //@@author A0141019U-reused
	/**
     * Case-insensitive substring method
     * @return true if query is contained in source
     */
	public static boolean containsIgnoreCase(String source, String query) {
        return source.toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
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
    
    //@@author A0141019U
    /**
     * Returns number of occurrences of the character in a string
     */
    public static int countOccurrences(char character, String string) {
    	int numOccurrences = 0;
    	char[] charArray = string.toCharArray();
    	
    	for (int i=0; i<charArray.length; i++) {
    		if (charArray[i] == character) {
    			numOccurrences += 1;
    		}
    	}
    	
    	return numOccurrences;
    }
    
    /**
	 * @param arguments
	 *            an input command string that may contain 0 or 2 single quotes
	 *            that surround a task name
	 * @return the name of the task enclosed by single quotes if it exists, an
	 *         empty string otherwise
	 */
	public static String getQuotedText(String arguments) {
		Prefix quotePrefix = new Prefix("'");
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(quotePrefix);
		argsTokenizer.tokenize(arguments);
		
		Optional<List<String>> maybeName = argsTokenizer.getAllValues(quotePrefix);
		
		if (maybeName.isPresent()) {
			return "'" + maybeName.get().get(0) + "'";
		}
		else {
			return "";
		}
	}

	/**
	 * @param arguments
	 *            an input command string that may contain 0 or 2 single quotes
	 *            that surround a task name
	 * @return the arguments before the quoted text concatenated with the
	 *         arguments after the quotes text
	 */
	public static String getNonQuotedText(String arguments) {
		Prefix quotePrefix = new Prefix("'");
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(quotePrefix);
		argsTokenizer.tokenize(arguments);
		
		String argsBeforeQuotes = argsTokenizer.getPreamble().orElse("");
		String argsAfterQuotes = argsTokenizer.getValue(quotePrefix).orElse("");
		
		return argsBeforeQuotes + " " + argsAfterQuotes;
	}
}
