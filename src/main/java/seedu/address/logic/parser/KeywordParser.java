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
    public KeywordParser(String... inputKeywords){
        this.keywords = new ArrayList<String>();
        for(String key: inputKeywords){
            this.keywords.add(key);
        }
    }
    /**
     * Parses input string arguments using keywords provided at construction
     * Substring associated with keyword starts after keyword, and before the next keyword or end of line
     * Keyword and associated substring put in a String array {Keyword, Substring}
     * Returns list of String arrays
     * @param string to be parsed
     * @return
     */
    public ArrayList<String[]> parse(String inputString){
        ArrayList<String[]> words = new ArrayList<String[]>();
        for(int i = 0; i < keywords.size(); i++){
            inputString = new String(inputString);
            String keyword = keywords.get(i);
            System.out.println("Keyword: " + keyword);
            String patternString = new String ("[^/]*" + keyword + " (?<returnString>[^/]+)");
            //String patternString = new String ("add (?<returnString>[^/]+) by[^/]+");
            if (i < keywords.size() - 1){
                patternString = patternString + " " + keywords.get(i + 1) + "[^/]+";
            }
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(inputString);
            if(matcher.matches()){
                String returnString = matcher.group("returnString");
                System.out.println(returnString);
                String[] returnPair = {keyword, returnString};
                words.add(returnPair);
            }
            else{
                System.out.println("No match");
            }
        }
        return words;
    }


}
