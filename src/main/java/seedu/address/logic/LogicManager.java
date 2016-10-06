package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.storage.Storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }
    
    @Override
    public String decideToolTip(String commandText){
        logger.info("----------------[INCOMPLETE USER COMMAND][" + commandText + "]");
        List<String> matchedCommands = parser.parseIncompleteCommand(commandText);
        
        // check if invalid command format
        if (matchedCommands.contains(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE))){
            return matchedCommands.get(0);
        }
        
        // check if unknown command typed
        if (matchedCommands.contains(Messages.MESSAGE_UNKNOWN_COMMAND)){
            return matchedCommands.get(0);
        }
        
        // return all matches
        StringBuilder stringBuilder = new StringBuilder();
        for (String matchedCommand : matchedCommands){
            stringBuilder.append(matchedCommand);
            stringBuilder.append("\n");
        }
        
        return stringBuilder.toString();
    }
}
