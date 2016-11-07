package seedu.tasklist.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@@author A0146107M
/**
 * Checks input for dates in dd/mm/yyyy format and converts it to mm/dd/yyyy
 * 
 */
public class TimePreparser {
	
	private static Pattern wrongDate = Pattern.compile("\\d*[/.-]\\d*([/.-]\\d*)?");
	private static Pattern dateType = Pattern
			.compile("(?<day>[012][0-9]|3[01])[/.-](?<month>[0]?[1-9]|1[012])([/.-](?<year>(19|20)\\d\\d))?");

	/**
	 * Checks input for dates in dd/mm/yyyy format and converts it to mm/dd/yyyy
	 * 
	 * @param	input String to be checked
	 */
	public static String preparse(String input) {
		//split string into tokens
		String[] tokens = input.split(" ");
		String result;
		if (tokens.length >= 1) {
			//parse all tokens
			result = parseAllTokens(tokens);
		} 
		else {
			result = tokens[0];
		}
		return result.trim();
	}

	/**
	 * Checks all tokens for dates in dd/mm/yyyy format and converts it to mm/dd/yyyy.
	 * 
	 * @param	tokens Strings to be checked
	 * @return	string with all tokens joined together, wrongdate if an invalid date was present
	 */
	private static String parseAllTokens(String[] tokens){
		//create empty result string
		String result = "";
		//interate through all tokens
		for (String token : tokens) {
			//parse token
			String parsedToken = checkTokenForDate(token);
			//check if token is invalid date
			if(parsedToken.equals("wrongdate")){
				result = parsedToken;
				return result;
			}
			else{
				//append to result
				result +=  parsedToken + " ";
			}
		}
		return result;
	}

	/**
	 * Checks token for date in dd/mm/yyyy format and converts it to mm/dd/yyyy,
	 * 
	 * @param	token String to be checked
	 * @return	the converted String, wrongdate if an invalid date was entered
	 */
	private static String checkTokenForDate(String token){
		//Matcher for dates
		Matcher matcher = dateType.matcher(token);
		//Matcher for dd/dd/dddd, where d is a digit
		Matcher wrongMatcher = wrongDate.matcher(token);
		//check if is valid date
		if (matcher.matches()) {
			//rearrange date
			token = rearrangeDate(matcher);
		}
		//check if is not valid date && is dd/dd/dddd => invalid date
		else if(wrongMatcher.matches()){
			//return a string that will cause Natty Parser to fail
			return "wrongdate";
		}
		//return rearranged token
		return token;
	}

	/**
	 * Rearranges date from dd/mm/yyyy to mm/dd/yyyy,
	 * 
	 * @param	matcher The matcher containing the date components
	 * @return	The converted String
	 */
	private static String rearrangeDate(Matcher matcher){
		return matcher.group("month") + "/" + matcher.group("day") + "/"
				+ ((matcher.group("year")==null)?"":matcher.group("year"));
	}

}
