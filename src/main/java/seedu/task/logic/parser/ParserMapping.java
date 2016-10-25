//@@author A0141052Y
package seedu.task.logic.parser;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.LogicManager;

/**
 * Provides command word and alias mappings
 * @author Syed Abdullah
 *
 */
public class ParserMapping {
    HashMap<String, Class<? extends BaseParser>> mappingTable = new HashMap<>();
    private final Logger logger = LogsCenter.getLogger(ParserMapping.class);
    
    public ParserMapping() {
        populateMappings();
    }
    
    /**
     * Populates the command word to command parsers mapping table
     */
    private void populateMappings() {
        mappingTable.put("add", AddParser.class);
        mappingTable.put("clear", ClearParser.class);
        mappingTable.put("complete", CompleteParser.class);
        mappingTable.put("delete", DeleteParser.class);
        mappingTable.put("exit", ExitParser.class);
        mappingTable.put("find", FindParser.class);
        mappingTable.put("help", HelpParser.class);
        mappingTable.put("list", ListParser.class);
        mappingTable.put("pin", PinParser.class);
        mappingTable.put("select", SelectParser.class);
        mappingTable.put("undo", UndoParser.class);
        mappingTable.put("update", UpdateParser.class);
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
            logger.info("[USER COMMAND][" + commandWord + "] not found!");
            return Optional.empty();
        }
    }
}
