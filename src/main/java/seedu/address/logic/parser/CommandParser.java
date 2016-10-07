package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

/**
 * This class should be extended by specialized parsers that want to 
 * parse a specific command. The text passed to such a parser will be 
 * guaranteed to have its header (the leading word) matching the String
 * returned by the abstract getHeader() method supplied by its implementation.
 *
 * @param <T> A Command that this Parser is going to produce upon successful parsing
 */
public abstract class CommandParser<T extends Command> {    
    /**
     * Indicates whether this parser would like to have the text supplied to it
     * preprocessed. The default behaviour is to allow preprocessing. 
     * Subclasses should override this method to return false if they do not want
     * the text to be preprocessed.
     * 
     * @return true if preprocessing is desired, false otherwise
     */
    protected boolean shouldPreprocess() {
        return true;
    }
    
    /**
     * Gets the header of this command parser.
     * The header is the first token of a command text, used to identify which 
     * CommandParser the command text will be dispatched to. Subclasses should
     * implement this method to indicate their header so that any command text that
     * starts with such a header will be given to them. This method should always 
     * return the same value, and must not return null or empty string.
     * 
     * @return header string
     */
    protected abstract String getHeader();
    
    /**
     * Parses the command text and returns the resulting command built from the parse.
     * Subclasses should implement this method to do their parsing. If the command text
     * is not as what they expect, subclasses should throw a ParseException.
     * 
     * @param commandText the command text
     * @return the resulting command object which can then be executed
     * @throws ParseException if the commandText has an incorrect syntax
     */
    protected abstract T parse(String commandText) throws ParseException;
}
