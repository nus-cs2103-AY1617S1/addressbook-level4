package seedu.manager.logic;

import javafx.collections.ObservableList;
import seedu.manager.commons.core.ComponentManager;
import seedu.manager.commons.core.LogsCenter;
import seedu.manager.logic.commands.Command;
import seedu.manager.logic.commands.CommandResult;
import seedu.manager.logic.parser.Parser;
import seedu.manager.model.Model;
import seedu.manager.model.activity.Activity;
import seedu.manager.storage.Storage;

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
    public ObservableList<Activity> getFilteredActivitiesList() {
        return model.getFilteredActivityList();
    }
}
