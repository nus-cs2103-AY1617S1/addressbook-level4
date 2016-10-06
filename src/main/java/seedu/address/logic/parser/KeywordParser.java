package seedu.address.logic.parser;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordParser {

    private List<String> keywords;

    /**
     * Constructor, input the keywords used to parse Strings
     * @param keywords
     */
    public KeywordParser(String... keywords){
        for(String key: keywords){
            this.keywords.add(key);
        }
    }

    public List<Map<String, String>> parse(){
        ArrayList<Map<String,String>> words = new ArrayList<Map<String, String>>();

        return words;
    }

}
