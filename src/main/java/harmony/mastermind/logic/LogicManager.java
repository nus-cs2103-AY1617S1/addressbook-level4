package harmony.mastermind.logic;

import javafx.collections.ObservableList;

import java.util.logging.Logger;

import harmony.mastermind.commons.core.ComponentManager;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.logic.commands.Command;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.parser.Parser;
import harmony.mastermind.model.Model;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText, String currentTab) {
        logger.info("----------------[" + currentTab + "Tab][USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText, currentTab);
        model.updateCurrentTab(currentTab);
        command.setData(model, storage);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredEventList() {
        return model.getFilteredEventList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return model.getFilteredDeadlineList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredArchiveList() {
        return model.getFilteredArchiveList();
    }
}
