package seedu.flexitrack.logic;

import javafx.collections.ObservableList;
import seedu.flexitrack.commons.core.ComponentManager;
import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.logic.commands.Command;
import seedu.flexitrack.logic.commands.CommandResult;
import seedu.flexitrack.logic.parser.Parser;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
}
