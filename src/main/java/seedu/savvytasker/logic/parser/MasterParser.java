//@@author A0139916U
package seedu.savvytasker.logic.parser;

import static seedu.savvytasker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savvytasker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.savvytasker.logic.commands.Command;
import seedu.savvytasker.logic.commands.HelpCommand;
import seedu.savvytasker.logic.commands.IncorrectCommand;
import seedu.savvytasker.model.alias.AliasSymbol;

public class MasterParser {
    private static final Pattern KEYWORD_PATTERN = 
            Pattern.compile("(\\S+)(\\s+|$)");
    
    private final Map<String, CommandParser<? extends Command>> commandParsers;
    private final Map<String, AliasSymbol> aliasingSymbols;
    
    public MasterParser() {
        this.commandParsers = new HashMap<String, CommandParser<? extends Command>>();
        this.aliasingSymbols = new HashMap<String, AliasSymbol>();
    }

    public Command parse(String userInput) {
        String[] pieces = preprocessInitial(userInput.trim());
        if (pieces == null) 
            return new IncorrectCommand(userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        
        String header = pieces[0];
        String body = pieces[1];
        String trueHeader = extractHeader(header);
        CommandParser<? extends Command> parser = commandParsers.get(trueHeader);
        if (parser == null)
            return new IncorrectCommand(header + body, String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
        if (parser.shouldPreprocess())
            body = preprocessBody(body);
        
        String combined = header + body;
        try {
            return parser.parse(combined);
        } catch (ParseException pe) {
            return new IncorrectCommand(combined, String.format(pe.getFailureDetails()));
        }
    }
    
    /**
     * Does an initial preprocessing of a command text in case the header is aliased.
     * Returns a string array with 2 elements: the first is the header which is possibly aliased,
     * which will be replaced with the representation of the aliasing, whereas the 
     * second is the body. If no header is found, this method returns null.
     * 
     * @param commandText the command text
     * @return a string array containing 2 elements, first is the header, second is the body; or null if
     * there is no header in the command text
     */
    private String[] preprocessInitial(String commandText) {
        Matcher matcher = KEYWORD_PATTERN.matcher(commandText);
        
        if (matcher.find()) {
            String header = matcher.group(1);
            String spaces = matcher.group(2);

            AliasSymbol symbol = aliasingSymbols.get(header);
            if (symbol != null) {
                header = symbol.getRepresentation();
            }
            
            String body = commandText.substring(matcher.end());
            return new String[] {header + spaces, body};
        }
        
        return null;
    }
    
    /**
     * Gets the header from the preprocessed header as a preprocessed header may contain
     * several tokens.
     * 
     * @param preprocessedHeader the preprocessed header
     * @return the true header
     */
    private String extractHeader(String preprocessedHeader) {
        Matcher matcher = KEYWORD_PATTERN.matcher(preprocessedHeader);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }
    
    /**
     * Preprocess the body to replace keywords with their replacement if they are aliased.
     * @param bodyText the body text
     * @return the preprocessed body text
     */
    private String preprocessBody(String bodyText) {
        StringBuilder builder = new StringBuilder();
        Matcher matcher = KEYWORD_PATTERN.matcher(bodyText);
        
        while (matcher.find()) {
            String keyword = matcher.group(1);
            String spaces = matcher.group(2); // Preserves the amount of spaces as that may be what user wants
            
            AliasSymbol symbol = aliasingSymbols.get(keyword);
            if (symbol != null)
                keyword = symbol.getRepresentation();
            
            builder.append(keyword);
            builder.append(spaces);            
        }
        
        return builder.toString();
    }
    
    /**
     * Registers a command parser that will be used by the master parser, and return true if it
     * is successfully registered. The header of this command parser should not be used by any 
     * other registered command parsers or used by any AliasSymbol whose keyword is the registered
     * with the same name, or false will be return and the parser will not be added. Use 
     * {@link #isCommandParserRegistered(String) isCommandParserRegistered } method to check if 
     * a command parser is already registered, and {@link #doesAliasSymbolExist(String) doesAliasSymbolExist}
     * 
     * Parameter commandParser should not be null.
     * 
     * @param commandParser the command parser
     * @return true if successfully registered, false if there is an parser with the same header 
     * already registered or if an alias with the same keyword is previously added.
     */
    public boolean registerCommandParser(CommandParser<? extends Command> commandParser) {
        assert commandParser != null;
        
        if (commandParsers.containsKey(commandParser.getHeader()))
            return false;
        if (aliasingSymbols.containsKey(commandParser.getHeader()))
            return false;
        
        commandParsers.put(commandParser.getHeader(), commandParser);   
        return true;
    }
    
    /**
     * Checks if a command parser with the specified header is already 
     * registered into the master parser.
     * 
     * @param header the header to check against
     * @return true if such a command parser is registered, false otherwise
     */
    public boolean isCommandParserRegistered(String header) {
        return commandParsers.containsKey(header);
    }
    
    /**
     * Unregisters and returns the command parser that uses the specified header.
     * If such a parser is not registered, null is returned.
     * 
     * @param header the header to check against
     * @return the CommandParser object that uses the specified header.
     */
    public CommandParser<? extends Command> unregisterCommandParser(String header) {
        return commandParsers.remove(header);
    }

    /**
     * Adds an aliasing symbol to be used by the parser to replace all such the symbol's keyword with 
     * its representation before parsing. If a symbol with an identical keyword exists, calling this
     * method has no effect and just returns false.
     * 
     * @param symbol the symbol, cannot be null.
     * @return true if this symbol is added successfully, false if another symbol with the same keyword
     * already exists or the keyword is used by a command and this symbol cannot be added.
     */
    public boolean addAliasSymbol(AliasSymbol symbol) {
        assert symbol != null;
        
        if (aliasingSymbols.containsKey(symbol.getKeyword()))
            return false;
        if (isCommandParserRegistered(symbol.getKeyword()))
            return false;
        
        aliasingSymbols.put(symbol.getKeyword(), symbol);
        return true;
    }
    
    /**
     * Removes an aliasing symbol, identified by its keyword. The parser will no longer replace the 
     * keyword of this symbol with its representation before parsing.
     * 
     * @param symbol the symbol to remove, cannot be null
     * @return true if the symbol exists and is removed, false otherwise
     */
    public boolean removeAliasSymbol(String symbolKeyword) {
        assert symbolKeyword != null;
        
        return aliasingSymbols.remove(symbolKeyword) != null;
    }
    
    /**
     * Returns true if a symbol with the specified keyword exists, false otherwise.
     * @param symbolKeyword the keyword to check for, cannot be null
     * @return true if the symbol exists, false otherwise
     */
    public boolean doesAliasSymbolExist(String symbolKeyword) {
        assert symbolKeyword != null;
        
        return aliasingSymbols.containsKey(symbolKeyword);
    }
    
    /**
     * Clears all existing symbols.
     * @see #removeAliasSymbol
     */
    public void clearAllAliasSymbols() {
        aliasingSymbols.clear();
    }
}
