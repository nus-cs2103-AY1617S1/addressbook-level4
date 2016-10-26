package seedu.tasklist.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//@@author A0146107M
public class TimePreparser {

    public static String preparse(String input) {
        String[] tokens = input.split(" ");
        String result = "";
        if (tokens.length >= 1) {
        	Pattern wrongDate = Pattern.compile("\\d*[/.-]\\d*([/.-]\\d*)?");
            Pattern dateType = Pattern
                    .compile("(?<day>[012][0-9]|3[01])[/.-](?<month>[0]?[1-9]|1[012])([/.-](?<year>(19|20)\\d\\d))?");
            for (String token : tokens) {
                Matcher matcher = dateType.matcher(token);
                Matcher wrongMatcher = wrongDate.matcher(token);
                if (matcher.matches()) {
                    String rearrangedDate = matcher.group("month") + "/" + matcher.group("day") + "/"
                            + ((matcher.group("year")==null)?"":matcher.group("year"));
                    token = rearrangedDate;
                }
                else if(wrongMatcher.matches()){
                	return "wrongdate";
                }
                result +=  token + " ";
            }
        } 
        else {
            result = tokens[0];
        }
        return result.trim();
    }
}
