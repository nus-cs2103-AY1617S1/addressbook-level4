package seedu.ggist.logic;

import javafx.collections.ObservableList;
import seedu.ggist.commons.core.ComponentManager;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.logic.commands.Command;
import seedu.ggist.logic.commands.CommandResult;
import seedu.ggist.logic.parser.Parser;
import seedu.ggist.model.Model;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.storage.Storage;

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
    
    @Override
    public ObservableList<ReadOnlyTask> getSortedTaskList() {
        return model.getSortedTaskList();
    }
    
    @Override
    public String getListing() {
        return model.getLastListing();
    }
}
