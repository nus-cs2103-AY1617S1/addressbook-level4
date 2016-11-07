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
		String[] tokens = input.split(" ");
		String result;
		if (tokens.length >= 1) {
			result = parseAllTokens(tokens);
			System.out.println(result);
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
		String result = "";
		for (String token : tokens) {
			String parsedToken = checkTokenForDate(token);
			if(parsedToken.equals("wrongdate")){
				result = parsedToken;
				return result;
			}
			else{
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
		Matcher matcher = dateType.matcher(token);
		Matcher wrongMatcher = wrongDate.matcher(token);
		if (matcher.matches()) {
			token = rearrangeDate(matcher);
		}
		else if(wrongMatcher.matches()){
			return "wrongdate";
		}
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
