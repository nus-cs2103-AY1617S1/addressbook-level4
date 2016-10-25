package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

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
    
    //@@author A0142184L
    @Override
    public ObservableList<ReadOnlyTask> getNonDoneTaskList() {
        return model.getNonDoneTaskList();
    }

	@Override
	public ObservableList<ReadOnlyTask> getTodayTaskList() {
		return model.getTodayTaskList();
	}

	@Override
	public ObservableList<ReadOnlyTask> getTomorrowTaskList() {
		return model.getTomorrowTaskList();
	}

	@Override
	public ObservableList<ReadOnlyTask> getIn7DaysTaskList() {
		return model.getIn7DaysTaskList();
	}

	@Override
	public ObservableList<ReadOnlyTask> getIn30DaysTaskList() {
		return model.getIn30DaysTaskList();
	}

	@Override
	public ObservableList<ReadOnlyTask> getSomedayTaskList() {
		return model.getSomedayTaskList();
	}

}
