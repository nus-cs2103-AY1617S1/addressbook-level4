package seedu.stask.logic;

import javafx.collections.ObservableList;
import seedu.stask.commons.core.ComponentManager;
import seedu.stask.commons.core.LogsCenter;
import seedu.stask.logic.commands.Command;
import seedu.stask.logic.commands.CommandResult;
import seedu.stask.logic.parser.Parser;
import seedu.stask.model.Model;
import seedu.stask.model.task.ReadOnlyTask;
import seedu.stask.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredDatedTaskList() {
        return model.getFilteredDatedTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredUndatedTaskList() {
        return model.getFilteredUndatedTaskList();
    }
    
}
