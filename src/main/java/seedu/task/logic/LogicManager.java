package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.UndoableCommand;
import seedu.task.logic.parser.ParseSwitcher;
import seedu.task.model.Model;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final ParseSwitcher parser;
    private UndoableCommand previousCommand;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new ParseSwitcher();
        this.previousCommand = null;
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, previousCommand);
        CommandResult result = command.execute();
        setPreviousCommand(result.isSuccessful, command);
        return result;
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    private void setPreviousCommand(boolean isSuccessful, Command command) {
        if (isSuccessful && command instanceof UndoableCommand) {
            this.previousCommand = (UndoableCommand) command;
        } else {
            this.previousCommand = null;
        }
    }
}
