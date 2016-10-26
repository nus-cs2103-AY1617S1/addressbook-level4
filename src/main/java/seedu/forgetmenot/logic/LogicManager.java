package seedu.forgetmenot.logic;

import javafx.collections.ObservableList;
import seedu.forgetmenot.commons.core.ComponentManager;
import seedu.forgetmenot.commons.core.LogsCenter;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.logic.commands.Command;
import seedu.forgetmenot.logic.commands.CommandResult;
import seedu.forgetmenot.logic.parser.Parser;
import seedu.forgetmenot.model.Model;
import seedu.forgetmenot.model.task.ReadOnlyTask;
import seedu.forgetmenot.storage.Storage;

import java.util.function.Predicate;
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
