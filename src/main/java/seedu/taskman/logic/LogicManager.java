package seedu.taskman.logic;

import javafx.collections.ObservableList;
import seedu.taskman.commons.core.ComponentManager;
import seedu.taskman.commons.core.LogsCenter;
import seedu.taskman.logic.commands.Command;
import seedu.taskman.logic.commands.CommandResult;
import seedu.taskman.logic.parser.Parser;
import seedu.taskman.model.Model;
import seedu.taskman.model.task.ReadOnlyTask;
import seedu.taskman.storage.Storage;

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
