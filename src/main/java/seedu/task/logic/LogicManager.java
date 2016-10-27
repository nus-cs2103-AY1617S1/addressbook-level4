package seedu.task.logic;

import javafx.collections.ObservableList;

import seedu.task.logic.commands.UndoableCommand;
import seedu.task.commons.exceptions.UndoableException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.parser.ParserManager;
import seedu.task.model.Model;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.storage.Storage;
import seedu.taskcommons.core.ComponentManager;
import seedu.taskcommons.core.LogsCenter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.logging.Logger;

/**
 * The main LogicManager of dowat.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final ParserManager parser;
    private UndoableCommandHistory commandList;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new ParserManager();
        this.commandList = new UndoableCommandHistory();
    }
    //@@author A0144702N
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        if(command instanceof UndoableCommand) {
        	UndoableCommand undoableCommand = (UndoableCommand) command;
        	commandList.add(undoableCommand);
        }
        command.setData(model);
        command.setCommandHistory(commandList);
        
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
    	model.updateFilteredTaskListToShowWithStatus(false);
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
    	model.updateFilteredEventListToShowWithStatus(false);
        return model.getFilteredEventList();
    }
    
    @Override
    public List<ReadOnlyEvent> getAllEvents() {
    	return model.getTaskBook().getEventList();
    }
    
    
}
