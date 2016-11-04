package seedu.simply.logic;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.simply.commons.core.ComponentManager;
import seedu.simply.commons.core.Config;
import seedu.simply.commons.core.LogsCenter;
import seedu.simply.logic.commands.Command;
import seedu.simply.logic.commands.CommandResult;
import seedu.simply.logic.parser.Parser;
import seedu.simply.model.Model;
import seedu.simply.model.SaveState;
import seedu.simply.model.TaskBook;
import seedu.simply.model.task.ReadOnlyTask;
import seedu.simply.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    //private final Config config;
    //private final Stack<SaveState> undoStack;
    //private final Stack<SaveState> redoStack;

    public LogicManager(Model model) /*, Storage storage, Config config)*/ {
        this.model = model;
        this.parser = new Parser();
        //this.config = config;
        //this.undoStack = new Stack<SaveState>();
        //this.redoStack = new Stack<SaveState>();
        model.overdueTask();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model); //, undoStack, redoStack, config);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredEventList() {
        model.updateFilteredListToShowAllUncompleted();
        return model.getFilteredEventList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return model.getFilteredDeadlineList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTodoList() {
        return model.getFilteredTodoList();
    }
}
