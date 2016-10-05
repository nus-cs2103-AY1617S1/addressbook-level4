package seedu.smartscheduler.logic;

import javafx.collections.ObservableList;
import seedu.smartscheduler.commons.core.ComponentManager;
import seedu.smartscheduler.commons.core.LogsCenter;
import seedu.smartscheduler.logic.commands.Command;
import seedu.smartscheduler.logic.commands.CommandResult;
import seedu.smartscheduler.logic.parser.Parser;
import seedu.smartscheduler.model.Model;
import seedu.smartscheduler.model.task.ReadOnlyTask;
import seedu.smartscheduler.storage.Storage;

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
    public ObservableList<ReadOnlyTask> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }
}
