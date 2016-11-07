package jym.manager.logic;

import javafx.collections.ObservableList;

import java.util.logging.Logger;

import jym.manager.commons.core.ComponentManager;
import jym.manager.commons.core.Config;
import jym.manager.commons.core.LogsCenter;
import jym.manager.logic.commands.Command;
import jym.manager.logic.commands.CommandResult;
import jym.manager.logic.parser.Parser;
import jym.manager.model.Model;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Storage storage;
    private final Config config;

    public LogicManager(Model model, Storage storage, Config config) {
        this.model = model;
        this.parser = new Parser();
        this.storage = storage;
        this.config = config;
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setData(storage);
        command.setData(config);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
  //@@author a0153617e

    @Override
    public ObservableList<ReadOnlyTask> getFilteredCompleteTaskList() {
        return model.getFilteredCompleteTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return model.getFilteredIncompleteTaskList();
    }
    
    @Override
    public void setCurrentTab(String tab) {
        model.setCurrentTab(tab);
    }
}
