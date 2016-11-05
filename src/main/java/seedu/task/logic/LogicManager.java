package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.parser.CommandParser;
import seedu.task.model.Model;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.storage.Storage;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final CommandParser parser;
    private final HistoryManager historyManager;

    //@@author A0147335E-reused
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new CommandParser();
        this.historyManager = new HistoryManager();
    }
    
    //@@author A0147335E-reused
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setHistory(historyManager);
        
        if (command instanceof IncorrectCommand) {
            return command.execute(false);
        }
        
        logger.info("SUCCESS");

        if (!isUndo(commandText)) {
            getPreviousCommandList().add(commandText);
            return command.execute(false);
        } else {
            return command.execute(true);
        }
    }

    //@@author A0147335E
	private boolean isUndo(String commandText) {
		return commandText.toLowerCase().startsWith(UndoCommand.COMMAND_WORD);
	}

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    //@@author A0147335E
    @Override
    public ArrayList<RollBackCommand> getUndoList() {
        return historyManager.getUndoList();
    }

    //@@author A0147335E
    @Override
    public ArrayList<String> getPreviousCommandList() {
        return historyManager.getPreviousCommandList();
    }
}