package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.spreadsheet.SpreadsheetCellType.DateType;

import seedu.address.commons.exceptions.IllegalValueException;

public class DatePreParse {
	
	 public static final String MESSAGE_INCORRECT_DATE_FORMAT = "The date provided is invalid. Follow the dd/mm/yy format and make sure a valid date is provided";
	
	public static String preparse(String input) throws IllegalValueException{
		String[] tokens = input.split(" ");
		String[] date = new String[3];
		String result = "";
//		Pattern dateType = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d)");
		
		for(String token: tokens){
//			Matcher matcher = dateType.matcher(token);
			if(token.contains("/")) {
				if(isValidDate(token, date)) { //matcher.group(1), matcher.group(2), matcher.group(3))) {
					String rearrangedDate = date[1] + "/" + date[0]
										+ "/" + date[2]; // Rearrange the date in mm/dd/yy format for Natty to understand
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

	public static boolean isValidDate(String token, String[] date) {
		
		Pattern dateType = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d)");
		Matcher matcher = dateType.matcher(token);
		
		if(!matcher.matches()){
			return false;
		} else {
			int day = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int year = Integer.parseInt(matcher.group(3));
			date[0] = matcher.group(1);
			date[1] = matcher.group(2);
			date[2] = matcher.group(3);
					
			
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
}
