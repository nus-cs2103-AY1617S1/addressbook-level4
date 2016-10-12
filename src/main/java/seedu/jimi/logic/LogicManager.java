package seedu.jimi.logic;

import javafx.collections.ObservableList;
import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.logic.commands.Command;
import seedu.jimi.logic.commands.CommandResult;
import seedu.jimi.logic.parser.JimiParser;
import seedu.jimi.model.Model;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final JimiParser jimiParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.jimiParser = new JimiParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = jimiParser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
