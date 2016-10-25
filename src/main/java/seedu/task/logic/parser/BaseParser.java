package seedu.task.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

import seedu.task.logic.commands.Command;

public abstract class BaseParser {
        
    /**
     * Extracts out arguments from the user's input
     * @param userInput full (or partial) user input string
     * @return a mapping of the keyword and the value(s) associated to it.
     * the value mapped to the empty string ("") is the non-keyword argument
     */
    private HashMap<String, ArrayList<String>> extractArguments(String userInput) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        String[] segments = userInput.split(" ");
        String currentKey = "";
        StringJoiner joiner = new StringJoiner(" ");
        
        for (String segment : segments) {
            if (segment.contains("/")) {
                ArrayList<String> arrayItems;
                if (result.containsKey(currentKey)) {
                    arrayItems = result.get(currentKey);
                } else {
                    arrayItems = new ArrayList<String>();
                }
                
                arrayItems.add(joiner.toString());
                result.put(currentKey, arrayItems);
                
                // set to next keyword
                currentKey = segment.split("/", 2)[0];
                continue;
            } else {
                joiner.add(segment);
            }
        }
        
        return result;
    }
    
    /**
     * Parses the user's input and determines the appropriate command
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public abstract Command parse(String userInput);
}
