package seedu.emeraldo.logic;

import javafx.collections.ObservableList;
import seedu.emeraldo.commons.core.ComponentManager;
import seedu.emeraldo.commons.core.LogsCenter;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.logic.commands.Command;
import seedu.emeraldo.logic.commands.CommandResult;
import seedu.emeraldo.logic.parser.Parser;
import seedu.emeraldo.model.Model;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.storage.Storage;

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
        Command command = null;
		try {
			command = parser.parseCommand(commandText);
		} catch (IllegalValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
