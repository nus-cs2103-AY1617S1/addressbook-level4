package seedu.address.logic.parser;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordParser {

    private ArrayList<String> keywords;
    private String KEYWORD_PARSER_NO_MATCHES = "No matches found for given keywords";

    /**
     * Constructor
     * @param keywords used to pass strings
     */
    public KeywordParser(String... inputKeywords){
        this.keywords = new ArrayList<String>();
        for(String key: inputKeywords){
            this.keywords.add(key);
        }
    }
    /**
     * Parses input string arguments using keywords provided at construction
     * Substring associated with keyword starts after keyword, and ends before the next keyword or end of line
     * Keyword and associated substring put in a HashMap, with key = keyword and value = associated substring
     * If no match found then empty HashMap returned
     * @param string to be parsed
     * @return HashMap containing the keyword - associated substring pairs
     */
    //TODO Make parsing not depend on order of input keywords
    public HashMap<String, String> parse(String inputString){
        HashMap<String, String> words = new HashMap<String, String>();
        for(int i = 0; i < keywords.size(); i++){
            inputString = new String(inputString);
            String keyword = keywords.get(i);
            String patternString;
            if (keyword.equals("add")) {
                //Special case for add command, which takes ""
                patternString = new String("[^/]*" + keyword + " " + "\"(?<returnString>[^/]+)\"");
            }
            else{
                patternString = new String("[^/]*" + keyword + " " + "(?<returnString>[^/]+)");
            }
            if (i < keywords.size() - 1){
                patternString = patternString + " " + keywords.get(i + 1) + "[^/]+";
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(inputString);
            if(matcher.matches()){
                String returnString = matcher.group("returnString");
                words.put(keyword, returnString);
            }

        }
        return words;
    }

    /**
     * Similar to parse(), but for input string with one keyword
     * Returns first match with any keyword. Associated substring is everything after the keyword
     * @param inputString
     * @return HashMap containing the keyword - associated substring pair
     */
    public HashMap<String, String> parseOne(String inputString){
        HashMap<String, String> words = new HashMap<String, String>();
        for(int i = 0; i < keywords.size(); i++){
            inputString = new String(inputString);
            String keyword = keywords.get(i);
            String patternString;
            if (keyword.equals("add")) {
                //Special case for add command, which takes ""
                patternString = new String("[^/]*" + keyword + " " + "\"(?<returnString>[^/]+)\"");
            }
            else{
                patternString = new String("[^/]*" + keyword + " " + "(?<returnString>[^/]+)");
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(inputString);
            if(matcher.matches()){
                String returnString = matcher.group("returnString");
                words.put(keyword, returnString);
                return words;
            }

        }
        return words;
    }


}
