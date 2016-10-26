//@@author A0139916U
package seedu.savvytasker.logic.parser;

import seedu.savvytasker.logic.commands.Command;

/**
 * This interface should be implemented by specialized parsers that want to 
 * parse a specific command. The text passed to such a parser will be 
 * guaranteed to have its header (the leading word) matching the String
 * returned by the abstract getHeader() method supplied by its implementation.
 *
 * @param <T> A Command that this Parser is going to produce upon successful parsing
 */
public interface CommandParser<T extends Command> {    
    /**
     * Indicates whether this parser would like to have the text supplied to it
     * preprocessed. The default behaviour is to allow preprocessing. 
     * Subclasses should override this method to return false if they do not want
     * the text to be preprocessed.
     * 
     * @return true if preprocessing is desired, false otherwise
     */
    public default boolean shouldPreprocess() {
        return true;
    }
    
    /**
     * Gets the header of this command parser.
     * The header is the first token of a command text, used to identify which 
     * CommandParser the command text will be dispatched to. Subclasses should
     * implement this method to indicate their header so that any command text that
     * starts with such a header will be given to them. This method should always 
     * return the same value, must return a string as a single token with no whitespaces 
     * in between, and must not return null or empty string.
     * 
     * @return header string
     */
    public String getHeader();
    
    /**
     * Gets the human-readable format of the string required by this command parser to 
     * parse correctly. Subclasses should implement this method to provide the above described.
     * This method should not return null, and should return the same value every time.
     * 
     * @return a human-readable format required by this parser
     */
    public String getRequiredFormat();
    
    /**
     * Parses the command text and returns the resulting command built from the parse.
     * Subclasses should implement this method to do their parsing. If the command text
     * is not as what they expect, subclasses should throw a ParseException.
     * 
     * @param commandText the full command text, including the header
     * @return the resulting command object which can then be executed
     * @throws ParseException if the commandText has an incorrect syntax
     */
    public T parse(String commandText) throws ParseException;
    
}
