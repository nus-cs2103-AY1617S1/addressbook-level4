package seedu.address.logic.parser;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordParser {

    private ArrayList<String> keywords;
    private String KEYWORD_PARSER_NO_MATCHES = "No matches found for given keywords";

    /**
     * Constructor
     * @param keywords used to pass strings
     */
    public KeywordParser(String... keywords){
        for(String key: keywords){
            this.keywords.add(key);
        }
    }
    /**
     * Parses input string arguments using keywords provided at construction
     * Substring associated with keyword starts after keyword, and before the next keyword or end of line
     * Keyword and associated substring put in a Map<Keyword, Associated substring>
     * Returns list of Maps
     * @param string to be parsed
     * @return
     */
    public List<Map<String, String>> parse(String inputString){
        ArrayList<Map<String,String>> words = new ArrayList<Map<String, String>>();
        for(int i = 0; i < keywords.size(); i++){
            String keyword = keywords.get(i);
            String patternString = keyword + "(?<returnString>[^/]+)";
            if (i < keywords.size() - 1){
                patternString = patternString + keywords.get(i + 1);
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(inputString.trim());
            if(matcher.matches()){
                String returnString = matcher.group("returnString");
            }

        }

        return words;
    }

}
