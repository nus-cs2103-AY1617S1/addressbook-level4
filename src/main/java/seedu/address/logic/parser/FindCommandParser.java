package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.models.FindCommandModel;
import seedu.address.model.task.FindType;

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
            FindType findType = parseFindType(commandText, matcher.group(REGEX_REF_FIND_TYPE));
            String[] keywords = parseKeywords(commandText,
                    matcher.group(REGEX_REF_KEYWORDS_BEFORE_TYPE),
                    matcher.group(REGEX_REF_KEYWORDS_AFTER_TYPE));
            
            // TODO: Return FindCommand here (require integration).
            
            return new FindCommand(new FindCommandModel(findType, keywords));
        }
        
        throw new ParseException(commandText, getRequiredFormat());
    }
    
    private FindType parseFindType(String commandText, String findTypeText) throws ParseException {
        try {
            return FindType.valueOfIgnoreCase(findTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(commandText, "FIND_TYPE: Unknown type '" + findTypeText + "'");
        }
    }
    
    private String[] parseKeywords(String commandText, String keywordsBefore, String keywordsAfter) throws ParseException {
        String[] keywordsArr1 = keywordsBefore.trim().split("\\s+");
        String[] keywordsArr2 = keywordsAfter.trim().split("\\s+");

        if (keywordsArr1.length == 0 && keywordsArr2.length == 0)
            throw new ParseException(commandText, "KEYWORD: Need to specify at least one keyword!");
        
        return concatArray(keywordsArr1, keywordsArr2);
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
