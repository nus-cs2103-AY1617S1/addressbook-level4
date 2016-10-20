package seedu.gtd.logic;

import seedu.gtd.commons.core.ComponentManager;
import seedu.gtd.commons.core.LogsCenter;
import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.logic.commands.Command;
import seedu.gtd.logic.commands.CommandResult;
import seedu.gtd.logic.parser.Parser;
import seedu.gtd.model.Model;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.storage.Storage;

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
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
