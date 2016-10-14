package seedu.savvytasker.logic.parser;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.logic.commands.FindCommand;
import seedu.savvytasker.logic.commands.models.FindCommandModel;
import seedu.savvytasker.model.task.FindType;

public class FindCommandParser implements CommandParser<FindCommand> {
    private static final String HEADER = "find";
    private static final String READABLE_FORMAT = "find [t/FIND_TYPE] KEYWORD [MORE_KEYWORDS]";

    private static final String REGEX_REF_KEYWORDS_BEFORE_TYPE = "Before";
    private static final String REGEX_REF_FIND_TYPE = "FindType";
    private static final String REGEX_REF_KEYWORDS_AFTER_TYPE = "After";
    
    private static final Pattern REGEX_PATTERN = Pattern.compile(
            HEADER+"\\s+(?<"+REGEX_REF_KEYWORDS_BEFORE_TYPE+">([^/]+(\\s+|$))*)" +
            "(t/(?<"+REGEX_REF_FIND_TYPE+">[^/]+?)(?!.*\\st/)(\\s+|$))?" +
            "(?<"+REGEX_REF_KEYWORDS_AFTER_TYPE+">([^/]+(\\s+|$))*)"
            , Pattern.CASE_INSENSITIVE);
    
    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getRequiredFormat() {
        return READABLE_FORMAT;
    }

    @Override
    public FindCommand parse(String commandText) throws ParseException {
        Matcher matcher = REGEX_PATTERN.matcher(commandText);
        if (matcher.matches()) {
            FindType findType = parseFindType(matcher.group(REGEX_REF_FIND_TYPE));
            String[] keywords = parseKeywords(matcher.group(REGEX_REF_KEYWORDS_BEFORE_TYPE),
                    matcher.group(REGEX_REF_KEYWORDS_AFTER_TYPE));
            
            return new FindCommand(new FindCommandModel(findType, keywords));
        }
        
        throw new ParseException(commandText, getRequiredFormat());
    }
    
    private FindType parseFindType(String findTypeText) throws ParseException {
        try {
            if (findTypeText == null)
                return null;
            findTypeText = findTypeText.trim();
            return FindType.valueOfIgnoreCase(findTypeText);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(findTypeText, "FIND_TYPE: Unknown type '" + findTypeText + "'");
        }
    }
    
    private String[] parseKeywords(String keywordsBefore, String keywordsAfter) throws ParseException {
        keywordsBefore = keywordsBefore.trim();
        keywordsAfter = keywordsAfter.trim();
        
        String[] keywordsArr1 = new String[0];
        String[] keywordsArr2 = new String[0];
        if (!keywordsBefore.isEmpty()) keywordsArr1 = keywordsBefore.split("\\s+");
        if (!keywordsAfter.isEmpty()) keywordsArr2 = keywordsAfter.split("\\s+");
        
        if (keywordsArr1.length == 0 && keywordsArr2.length == 0)
            throw new ParseException(keywordsBefore + " ... " + keywordsAfter,
                    "KEYWORD: Need to specify at least one keyword!");
        
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
