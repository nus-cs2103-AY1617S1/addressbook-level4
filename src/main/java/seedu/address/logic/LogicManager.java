package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.history.History;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.item.ReadOnlyTask;
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
    private final History history;

    public LogicManager(Model model, Storage storage, History history) {
        this.model = model;
        this.parser = new Parser();
        this.history = history;
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, history);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }
    
    @Override
    public String decideToolTip(String commandText){
        //logger.info("----------------[INCOMPLETE USER COMMAND][" + commandText + "]");
        List<String> toolTips = parser.parseIncompleteCommand(commandText);
        
        // toolTips should at least have 1 item
        assert toolTips.size() > 0;
        
        // check if invalid command format
        if (toolTips.contains(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE))){
            assert toolTips.size() == 1;
            return toolTips.get(0);
        }
        
        // check if unknown command typed
        if (toolTips.contains(Messages.MESSAGE_UNKNOWN_COMMAND)){
            assert toolTips.size() == 1;
            return toolTips.get(0);
        }
        
        // return all matches
        StringBuilder stringBuilder = new StringBuilder();
        for (String toolTip : toolTips){
            stringBuilder.append(toolTip);
            stringBuilder.append("\n");
        }
        
        // return the tooltip as a single string separated by \n
        return stringBuilder.toString();
    }
}
