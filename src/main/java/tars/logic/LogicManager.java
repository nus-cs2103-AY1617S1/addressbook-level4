package tars.logic;

import javafx.collections.ObservableList;
import tars.commons.core.ComponentManager;
import tars.commons.core.LogsCenter;
import tars.logic.commands.Command;
import tars.logic.commands.CommandResult;
import tars.logic.parser.Parser;
import tars.model.Model;
import tars.model.task.ReadOnlyTask;
import tars.model.task.rsv.RsvTask;
import tars.storage.Storage;

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
    /** @@author A0139924W */
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<RsvTask> getFilteredRsvTaskList() {
        return model.getFilteredRsvTaskList();
    }
    
}
