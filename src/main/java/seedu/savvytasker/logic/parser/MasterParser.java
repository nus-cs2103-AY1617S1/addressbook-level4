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
    private static final Pattern BASIC_COMMAND_FORMAT = 
            Pattern.compile("(?<header>\\S+).*");
    
    private final Map<String, CommandParser<? extends Command>> commandParsers;
    private final Map<String, AliasSymbol> aliasingSymbols;
    
    public MasterParser() {
        this.commandParsers = new HashMap<String, CommandParser<? extends Command>>();
        this.aliasingSymbols = new HashMap<String, AliasSymbol>();
    }

    public Command parse(String userInput) {
        final String trimmedUserInput = userInput.trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedUserInput);
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String header = matcher.group("header");
        CommandParser<? extends Command> parser = commandParsers.get(header);
        if (parser == null) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        
        try {
            return parser.parse(trimmedUserInput);
        } catch (ParseException pe) {
            /* TODO: Modify this to use ParseException's detail. */
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, parser.getRequiredFormat()));
        }
    }
    
    /**
     * Registers a command parser that will be used by the master parser.
     * The header of this command parser must not be used by any other command parsers
     * that are currently registered into the master parser. Use {@link #isCommandParserRegistered(String)
     * isCommandParserRegistered } method to check if such a command parser is already registered.
     * 
     * Parameter commandParser should not be null.
     * 
     * @param commandParser the command parser
     */
    public void registerCommandParser(CommandParser<? extends Command> commandParser) {
        assert commandParser != null;
        assert commandParsers.get(commandParser.getHeader()) == null;
        
        commandParsers.put(commandParser.getHeader(), commandParser);        
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
     * already exists and this symbol cannot be added.
     */
    public boolean addAliasSymbol(AliasSymbol symbol) {
        assert symbol != null;
        
        if (aliasingSymbols.containsKey(symbol.getKeyword()))
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
