package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParser extends CommandParser<FindCommand> {
    private static final String HEADER = "find";
    private static final String READABLE_FORMAT = "find [t/FIND_TYPE] KEYWORD [MORE_KEYWORDS]";

    private static final String REGEX_REF_KEYWORDS_BEFORE_TYPE = "Before";
    private static final String REGEX_REF_FIND_TYPE = "FindType";
    private static final String REGEX_REF_KEYWORDS_AFTER_TYPE = "After";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            "find\\s+(?<"+REGEX_REF_KEYWORDS_BEFORE_TYPE+">([^/]+\\s+)*)" +
            "t/(?<"+REGEX_REF_FIND_TYPE+">[^/]+?)(?!.*\\st/)(\\s+|$)" +
            "(?<"+REGEX_REF_KEYWORDS_AFTER_TYPE+">([^/]+(\\s+|$))*)"
            , Pattern.CASE_INSENSITIVE);
    
    @Override
    protected String getHeader() {
        return HEADER;
    }

    @Override
    protected String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    protected FindCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            String[] keywords1 = matcher.group(REGEX_REF_KEYWORDS_BEFORE_TYPE).trim().split("\\s+");
            String[] keywords2 = matcher.group(REGEX_REF_KEYWORDS_AFTER_TYPE).trim().split("\\s+");
            
            if (keywords1.length == 0 && keywords2.length == 0)
                throw new ParseException(commandText, "Need to specify at least one keyword!");
            
            String[] keywords = concatArray(keywords1, keywords2);
            
        }
        
        throw new ParseException(commandText);
    }

    /**
     * Concatenates 2 arrays into 1 array, in the order the arrays are given.
     * 
     * @param array1 the first array
     * @param array2 the second array
     * @return the new array with the values from each of the given array added into it in order
     */
    private String[] concatArray(String[] array1, String[] array2) {
        String[] newArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);
        return newArray;
    }
}
