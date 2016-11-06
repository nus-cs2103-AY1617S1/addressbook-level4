package seedu.todo.controllers.concerns;

import java.util.HashMap;
import java.util.Map;

import seedu.todo.commons.util.StringUtil;

// @@author A0139812A
public class Disambiguator {
    
    private static final String TOKEN_NAME = "name";
    public static final String PLACEHOLDER_NAME = "<name>";
    private static final String TOKEN_TASKSTATUS = "taskStatus";
    public static final String PLACEHOLDER_TASKSTATUS = "<task status>";
    private static final String TOKEN_EVENTSTATUS = "eventStatus";
    public static final String PLACEHOLDER_EVENTSTATUS = "<event status>";
    private static final String TOKEN_TAG = "tag";
    public static final String PLACEHOLDER_TAG = "<tag>";
    private static final String TOKEN_STARTTIME = "startTime";
    public static final String PLACEHOLDER_STARTTIME = "<start>";
    private static final int INDEX_STARTTIME = 0;
    private static final String TOKEN_ENDTIME = "endTime";
    public static final String PLACEHOLDER_ENDTIME = "<end>";
    private static final int INDEX_ENDTIME = 1;
    
    /**
     * Extracts the first token from each type of token. 
     * If the token doesn't exist, it will be replaced with a default placeholder.
     * This is for the purpose of rendering disambiguation strings.
     */
    public static Map<String, String> extractParsedTokens(Map<String, String[]> parsedResult) {
        Map<String, String> tokens = new HashMap<>();
        
        // Extract tokens
        tokens.put(TOKEN_NAME, extractKeyOrValue(parsedResult, true, TOKEN_NAME, PLACEHOLDER_NAME));
        tokens.put(TOKEN_TASKSTATUS, extractKeyOrValue(parsedResult, false, TOKEN_TASKSTATUS, PLACEHOLDER_TASKSTATUS));
        tokens.put(TOKEN_EVENTSTATUS, extractKeyOrValue(parsedResult, false, TOKEN_EVENTSTATUS, PLACEHOLDER_EVENTSTATUS));
        tokens.put(TOKEN_TAG, extractKeyOrValue(parsedResult, true, TOKEN_TAG, PLACEHOLDER_TAG));
        
        // Time start/end
        String[] datePair = DateParser.extractDatePair(parsedResult);
        tokens.put(TOKEN_STARTTIME, StringUtil.replaceEmpty(datePair[INDEX_STARTTIME], PLACEHOLDER_STARTTIME));
        tokens.put(TOKEN_ENDTIME, StringUtil.replaceEmpty(datePair[INDEX_ENDTIME], PLACEHOLDER_ENDTIME));
        
        return tokens;
    }
    
    /**
     * Extracts the key or value of the token value array, and returns it.
     * Accepts a placeholder string in the event that the key or value doesn't exist.
     */
    private static String extractKeyOrValue(Map<String, String[]> parsedResult, boolean extractValue, String key, String placeholder) {
        int n = extractValue ? 1 : 0;
        
        // Extracts the key or value depending on extractKey.
        String extracted = null;
        if (parsedResult.get(key) != null && parsedResult.get(key).length > n) {
            extracted = parsedResult.get(key)[n];
        }
        
        // Replaces with placeholder if empty.
        extracted = StringUtil.replaceEmpty(extracted, placeholder);
        
        return extracted;
    }
    
    /**
     * Extracts any unknown token strings from the parsed result.
     */
    public static String getUnknownTokenString(Map<String, String[]> parsedResult) {
        String[] defaultToken = parsedResult.get("default");
        
        if (defaultToken != null && defaultToken[1] != null && defaultToken[1].length() > 0) {
            return defaultToken[1];
        } else {
            return null;
        }
    }
}
