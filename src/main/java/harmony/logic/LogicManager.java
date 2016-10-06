package harmony.logic;

import javafx.collections.ObservableList;

import java.util.logging.Logger;

import harmony.commons.core.ComponentManager;
import harmony.commons.core.LogsCenter;
import harmony.logic.commands.Command;
import harmony.logic.commands.CommandResult;
import harmony.logic.parser.Parser;
import harmony.model.Model;
import harmony.model.task.ReadOnlyTask;
import harmony.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }
}
