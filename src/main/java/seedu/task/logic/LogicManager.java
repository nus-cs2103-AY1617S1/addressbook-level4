package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.parser.ParserManager;
import seedu.task.model.Model;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.storage.Storage;
import seedu.taskcommons.core.ComponentManager;
import seedu.taskcommons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * The main LogicManager of dowat.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final ParserManager parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new ParserManager();
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

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }
}
