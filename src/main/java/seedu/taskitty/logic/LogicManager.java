package seedu.taskitty.logic;

import javafx.collections.ObservableList;
import seedu.taskitty.commons.core.ComponentManager;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.logic.commands.Command;
import seedu.taskitty.logic.commands.CommandResult;
import seedu.taskitty.logic.parser.CommandParser;
import seedu.taskitty.model.Model;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandParser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new CommandParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.saveStateIfNeeded(commandText);
        return command.execute();
    }
    
    //@@author A0139930B
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTodoList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return model.getFilteredDeadlineList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredEventList() {
        return model.getFilteredEventList();
    }
    
    //@@author A0130853L
    @Override
    public void initialiseList() {
    	model.initialiseFilteredList();
    }
}
