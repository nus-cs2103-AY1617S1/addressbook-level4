package seedu.menion.logic.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author A0146752B
public class DeleteParser {

    private static final Pattern REGULAR_TASK_REGEX = Pattern.compile("(.+) (.+)");
    

    private static Matcher matcher;

    public DeleteParser() {
    };

    private static ArrayList<String> parsedArguments; 

    public static ArrayList<String> parseArguments(String args) {

        matcher = REGULAR_TASK_REGEX.matcher(args);
        parsedArguments = new ArrayList<String>();
        if (matcher.find()) {
            String arg1 = matcher.group(1);
            String arg2 = matcher.group(2);
            parsedArguments.add(arg1);
            parsedArguments.add(arg2);
        }
        return parsedArguments;
    }
    
}
