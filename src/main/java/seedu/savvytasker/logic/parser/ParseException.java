//@@author A0139916U
package seedu.savvytasker.logic.parser;

/**
 * This exception should be thrown by parsers when they are unable to
 * parse a string of tokens.
 */
public class ParseException extends Exception {
    private static final long serialVersionUID = -1157747299012674373L;
    private final String parsedString;
    private final String failureDetails;
    
    /**
     * Constructs a ParseException object with the original string that
     * the parser failed to parse, with no further details to why the 
     * parser failed to parse the string
     * 
     * @param parsedString the erroneous string
     */
    public ParseException(String parsedString) {
        this(parsedString, "");
    }
    
    /**
     * Constructs a ParseException object with the original string that
     * the parser failed to parse, and further details to why the parser
     * failed to parse the string.
     * 
     * @param parsedString the erroneous string
     * @param failureDetails the failure details
     */
    public ParseException(String parsedString, String failureDetails) {
        super("Failed to parse " + parsedString + ". " + failureDetails);
        this.parsedString = parsedString;
        this.failureDetails = failureDetails;
    }
    
    public String getParsedString() {
        return this.parsedString;
    }
    
    public String getFailureDetails() {
        return this.failureDetails;
    }
}
