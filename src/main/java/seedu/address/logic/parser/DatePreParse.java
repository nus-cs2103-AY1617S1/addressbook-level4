package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatePreParse {
	
	public static String preparse(String input){
		String[] tokens = input.split(" ");
		String result = "";
		Pattern dateType = Pattern.compile("(?<day>[12][0-9]|3[01])[/-](?<month>[1-9]|1[012])[/-](?<year>\\d\\d)");
		
		for(String token: tokens){
			Matcher matcher = dateType.matcher(token);
			if(matcher.matches()){
				String rearrangedDate = matcher.group("month") + "/" + matcher.group("day")
									+ "/" + matcher.group("year"); // Rearrange the date in mm/dd/yy format for Natty to understand
				token = rearrangedDate;
			}
			result += token+" ";
		}
		System.out.println("Shailesh"+ result);
		return result.trim();
	}

}
