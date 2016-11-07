package seedu.lifekeeper.logic;

import javafx.collections.ObservableList;
import seedu.lifekeeper.commons.core.ComponentManager;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.logic.commands.Command;
import seedu.lifekeeper.logic.commands.CommandResult;
import seedu.lifekeeper.logic.parser.Parser;
import seedu.lifekeeper.model.Model;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.storage.Storage;


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
    public void resetData(ReadOnlyLifeKeeper newData) {
        this.model.resetData(newData);
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyActivity> getFilteredActivityList() {
        return model.getFilteredTaskList();
    }

	@Override
	public ObservableList<ReadOnlyActivity> getFilteredOverdueTaskList() {
		return model.getFilteredOverdueTaskList();
	}
    
}
