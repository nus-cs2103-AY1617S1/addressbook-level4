package seedu.dailyplanner.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.dailyplanner.commons.core.ComponentManager;
import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.logic.commands.Command;
import seedu.dailyplanner.logic.commands.CommandResult;
import seedu.dailyplanner.logic.parser.Parser;
import seedu.dailyplanner.model.Model;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.storage.Storage;

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
	//@@author A0146749N
	@Override
	public ObservableList<ReadOnlyTask> getPinnedTaskList() {
		return model.getPinnedTaskList();
	}

	@Override
	public IntegerProperty getLastTaskAddedIndexProperty() {
		return model.getLastTaskAddedIndexProperty();
	}

	@Override
	public StringProperty getLastShowDateProperty() {
		return model.getLastShowDateProperty();
	}

}
