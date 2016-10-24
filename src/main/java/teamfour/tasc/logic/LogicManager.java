package teamfour.tasc.logic;

import javafx.collections.ObservableList;
import teamfour.tasc.commons.core.ComponentManager;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.logic.commands.Command;
import teamfour.tasc.logic.commands.CommandResult;
import teamfour.tasc.logic.parser.Parser;
import teamfour.tasc.model.Model;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.storage.Storage;

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
        if (command.canUndo()) {
            model.saveTaskListHistory();
            model.clearRedoTaskListHistory();
        }
        CommandResult result = command.execute();
        return result;
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
