package seedu.task.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.StringJoiner;

import seedu.task.commons.util.StringUtil;
import seedu.task.logic.commands.Command;

public abstract class BaseParser {
    
    protected final HashMap<String, ArrayList<String>> argumentsTable = new HashMap<>();
        
    /**
     * Extracts out arguments from the user's input into a HashMap.
     * The value mapped to the empty string ("") is the non-keyword argument.
     * 
     * @param args full (or partial) user input arguments
     */
    protected void extractArguments(String args) {
        argumentsTable.clear();
        String[] segments = args.trim().split(" ");
        String currentKey = "";
        StringJoiner joiner = new StringJoiner(" ");
        
        for (String segment : segments) {
            if (segment.contains("/")) {
                addToArgumentsTable(currentKey, joiner.toString());
                
                String[] kwargComponent = segment.split("/", 2);
                
                // set to next keyword
                currentKey = kwargComponent[0];
                
                joiner = new StringJoiner(" ");
                if (kwargComponent.length > 1) {
                    joiner.add(kwargComponent[1]);
                }
                
                continue;
            } else {
                joiner.add(segment);
            }
        }
        
        addToArgumentsTable(currentKey, joiner.toString());
    }
    
    /**
     * Assigns a value to a keyword argument. Does not replace any existing
     * values associated with the keyword.
     * @param keyword
     * @param value
     */
    protected void addToArgumentsTable(String keyword, String value) {
        ArrayList<String> arrayItems;
        if (argumentsTable.containsKey(keyword)) {
            arrayItems = argumentsTable.get(keyword);
        } else {
            arrayItems = new ArrayList<String>();
        }
        
        arrayItems.add(value);
        System.out.println(keyword + ": " + value);
        argumentsTable.put(keyword, arrayItems);
    }
    
    protected boolean checkForRequiredArguments(String[] requiredArgs) {
        if (argumentsTable == null) {
            return false;
        }
        
        for (String arg : requiredArgs) {
            if (!argumentsTable.containsKey(arg)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Retrieves the value for the keyword argument
     * @param keyword the keyword of the argument
     * @return the current value of the keyword argument
     */
    protected String getSingleKeywordArgValue(String keyword) {
        if (argumentsTable.containsKey(keyword)) {
            return argumentsTable.get(keyword).get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Returns a positive integer, if the user supplied unnamed keyword argument is a positive integer.
     * Returns an {@code Optional.empty()} otherwise.
     */
    protected Optional<Integer> parseIndex() {
        String index = getSingleKeywordArgValue("");
        return parseIndex(index);
    }
    
    protected Optional<Integer> parseIndex(String index) {
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));
    }
    
    /**
     * Parses the user's input and determines the appropriate command
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public abstract Command parse(String command, String arguments);
}
