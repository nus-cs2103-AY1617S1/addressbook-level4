package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.taskcommands.TaskCommand;
import seedu.address.logic.parser.TaskCommandsParser;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.Task;
import seedu.address.storage.task.TaskStorage;

/**
 * The main LogicManager_Task of the app.
 */
public class LogicManager_Task extends ComponentManager implements Logic_Task {
    private final Logger logger = LogsCenter.getLogger(LogicManager_Task.class);

    private final InMemoryTaskList model;
    private final TaskCommandsParser parser;

    public LogicManager_Task(InMemoryTaskList model, TaskStorage storage) {
        this.model = model;
        this.parser = new TaskCommandsParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        TaskCommand command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
    	return model.getCurrentFilteredTasks().getInternalList();
    }
}
