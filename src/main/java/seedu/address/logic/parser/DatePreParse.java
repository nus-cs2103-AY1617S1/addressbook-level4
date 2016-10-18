package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

public class DatePreParse {
	
	 public static final String MESSAGE_INCORRECT_DATE_FORMAT = "The date provided is invalid. Follow the dd/mm/yy format and make sure a valid date is provided";
	
	public static String preparse(String input) throws IllegalValueException{
		String[] tokens = input.split(" ");
		String result = "";
		Pattern dateType = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d)");
		
		for(String token: tokens){
			Matcher matcher = dateType.matcher(token);
			if(token.contains("/")) {
				if(matcher.matches() && isValidDate(matcher.group(1), matcher.group(2), matcher.group(3))) {
					String rearrangedDate = matcher.group(2) + "/" + matcher.group(1)
										+ "/" + matcher.group(3); // Rearrange the date in mm/dd/yy format for Natty to understand
					token = rearrangedDate;
				}
				else {
					System.out.println("Illegal");
					throw new IllegalValueException(MESSAGE_INCORRECT_DATE_FORMAT);
				}
			}
			result += token+" ";
		}
		System.out.println("Shailesh"+ result);
		return result.trim();
	}

	private static boolean isValidDate(String dd, String mm, String yy) {
		
		int day = Integer.parseInt(dd);
		int month = Integer.parseInt(mm);
		int year = Integer.parseInt(yy);
		
		switch (month) {
	        case 1: case 3: case 5: case 7: case 8: case 10: case 12: 
	        	return day < 32;
	        case 4: case 6: case 9: case 11: 
	        	return day < 31;
	        case 2: 
	            if (year % 4 == 0)
	                return day < 30; //its a leap year
	            else
	                return day < 29;
	        default:
	            break;
        }
		return false;
	}

}
