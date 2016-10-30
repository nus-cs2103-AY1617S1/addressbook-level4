package seedu.taskscheduler.logic;

import javafx.collections.ObservableList;
import seedu.taskscheduler.commons.core.ComponentManager;
import seedu.taskscheduler.commons.core.LogsCenter;
import seedu.taskscheduler.logic.commands.Command;
import seedu.taskscheduler.logic.commands.CommandHistory;
import seedu.taskscheduler.logic.commands.CommandResult;
import seedu.taskscheduler.logic.parser.Parser;
import seedu.taskscheduler.model.Model;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.storage.Storage;

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
        return execute(commandText, true);
    }
    
    @Override
    public CommandResult execute(String commandText, boolean savePrevCommand) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        if (savePrevCommand) {
            CommandHistory.addPrevCommand(commandText);
        }
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getPriorityFilteredTaskList() {
        return model.getPriorityFilteredTaskList();
    }

    @Override
    public ObservableList<Tag> getUnmodifiableTagList() {
        return model.getUnmodifiableTagList();
    }
}
