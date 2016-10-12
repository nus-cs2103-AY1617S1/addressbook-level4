package seedu.inbx0.logic;

import javafx.collections.ObservableList;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.logic.commands.Command;
import seedu.inbx0.logic.commands.CommandResult;
import seedu.inbx0.logic.parser.Parser;
import seedu.inbx0.model.Model;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.storage.Storage;

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
