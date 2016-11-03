//@@author A0141052Y
package seedu.task.logic.parser;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.logic.CommandKeys;
import seedu.task.commons.logic.CommandKeys.Commands;

/**
 * Provides command word and alias mappings
 * @author Syed Abdullah
 *
 */
public class ParserMapping {
    HashMap<Commands, Class<? extends BaseParser>> mappingTable = new HashMap<>();
    private final Logger logger = LogsCenter.getLogger(ParserMapping.class);
    private final HashMap<String, Commands> aliasMappings;
    
    public ParserMapping(HashMap<String, Commands> aliasMappings) {
        populateMappings();
        this.aliasMappings = aliasMappings;
    }
       
    /**
     * Populates the command word to command parsers mapping table
     */
    private void populateMappings() {
        mappingTable.put(Commands.ADD, AddParser.class);
        mappingTable.put(Commands.ALIAS, AliasParser.class);
        mappingTable.put(Commands.CLEAR, ClearParser.class);
        mappingTable.put(Commands.COMPLETE, CompleteParser.class);
        mappingTable.put(Commands.CHANGE_TO, ChangePathParser.class);
        mappingTable.put(Commands.DELETE, DeleteParser.class);
        mappingTable.put(Commands.EXIT, ExitParser.class);
        mappingTable.put(Commands.FIND, FindParser.class);
        mappingTable.put(Commands.HELP, HelpParser.class);
        mappingTable.put(Commands.LIST, ListParser.class);
        mappingTable.put(Commands.PIN, PinParser.class);
        mappingTable.put(Commands.SEARCH_BOX, SearchParser.class);
        mappingTable.put(Commands.SELECT, SelectParser.class);
        mappingTable.put(Commands.UNDO, UndoParser.class);
        mappingTable.put(Commands.UPDATE, UpdateParser.class);
        mappingTable.put(Commands.UNCOMPLETE, UncompleteParser.class);
        mappingTable.put(Commands.UNPIN, UnpinParser.class);
    }

    /**
     * Retrieves commands for a specified keyword
     * @param commandWord
     * @return
     */
    public Optional<Class<? extends BaseParser>> getParserForCommand(String commandWord) {        
        
        //check if it's an alias
        if(aliasMappings.containsKey(commandWord) && aliasMappings.get(commandWord) != null) {
            Commands command = aliasMappings.get(commandWord);
            return Optional.of(mappingTable.get(command));
        } 
        
        if (CommandKeys.commandKeyMap.containsKey(commandWord) && CommandKeys.commandKeyMap.get(commandWord) != null) {
            Commands command = CommandKeys.commandKeyMap.get(commandWord);
            return Optional.of(mappingTable.get(command));
        } else {
            logger.info("[USER COMMAND][" + commandWord + "] not found!");
            return Optional.empty();
        }
    }
}