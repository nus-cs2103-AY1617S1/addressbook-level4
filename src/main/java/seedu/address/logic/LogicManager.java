package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.history.History;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.parser.CommandParser;
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
    private final History history;
    private final CommandParser parser;

    public LogicManager(Model model, Storage storage, History history) {
        this.model = model;
        this.history = history;
        this.parser = new CommandParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);    
        command.setData(model, history); 
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredUndoneTaskList() {
        return model.getFilteredUndoneTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDoneTaskList() {
        return model.getFilteredDoneTaskList();
    }
    
    /**
     * Generates the tool tip for the current user input.
     * 
     * @param commandText the user input string
     * @return the tooltip that fits the user input string
     */
    @Override
    public String generateToolTip(String commandText){
        assert commandText != null;
        
        List<String> toolTips = parser.parseForTooltip(commandText);
        
        // toolTips should have at least one string in it
        // as there is a default case (add command)
        assert toolTips != null && toolTips.size() > 0;        
                      
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
