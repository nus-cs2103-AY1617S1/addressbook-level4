package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

public class DatePreParse {
	
	public static String preparse(String input) throws IllegalValueException{
		String[] tokens = input.split(" ");
		String result = "";
		Pattern dateType = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d)");
		
		for(String token: tokens){
			Matcher matcher = dateType.matcher(token);
			if(token.contains("/")) {
				if(matcher.matches()) {
					String rearrangedDate = matcher.group(2) + "/" + matcher.group(1)
										+ "/" + matcher.group(3); // Rearrange the date in mm/dd/yy format for Natty to understand
					token = rearrangedDate;
				}
			}
			result += token+" ";
		}
		
		return result.trim();
	}
}
