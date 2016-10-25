//@@author A0141052Y
package seedu.task.logic.parser;

import java.util.HashMap;
import java.util.Optional;

/**
 * Provides command word and alias mappings
 * @author Syed Abdullah
 *
 */
public class ParserMapping {
    HashMap<String, Class<? extends BaseParser>> mappingTable = new HashMap<>();
    
    public ParserMapping() {
        populateMappings();
    }
    
    /**
     * Populates the command word to command parsers mapping table
     */
    private void populateMappings() {
        
    }

    /**
     * Retrieves commands for a specified keyword
     * @param commandWord
     * @return
     */
    public Optional<Class<? extends BaseParser>> getParserForCommand(String commandWord) {
        if (mappingTable.containsKey(commandWord) && mappingTable.get(commandWord) != null) {
            return Optional.of(mappingTable.get(commandWord));
        } else {
            return Optional.empty();
        }
    }
}
