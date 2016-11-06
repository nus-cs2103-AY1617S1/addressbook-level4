package seedu.forgetmenot.logic.parser;

//@@author A0147619W 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

public class DatePreParse {
	
	/**
	 * Rearranges the 'date' in the input to mm/dd/yy format so that Natty can parse it properly
	 * @param input
	 * @return the parsed version of the input time
	 * @throws IllegalValueException
	 */
	public static String preparse(String input) throws IllegalValueException{
		String[] tokens = input.split(" ");
		String result = "";
		Pattern dateType = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d)");
		
		for(String token: tokens){
			Matcher matcher = dateType.matcher(token);
			if(token.contains("/")) {
				if(matcher.matches()) {
					token = rearrangeDateFormat(matcher);
				}
			}
			result += token+" ";
		}
		
		return result.trim();
	}

	/**
	 * Returns the rearranged format of the date
	 * @param matcher
	 */
	private static String rearrangeDateFormat(Matcher matcher) {
		String rearrangedDate = matcher.group(2) + "/" + matcher.group(1) 
							+ "/" + matcher.group(3); // Rearrange the date in mm/dd/yy format for Natty to understand
		return rearrangedDate;
	}
}
