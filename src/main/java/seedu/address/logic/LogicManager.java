package seedu.address.logic;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.SaveState;
import seedu.address.model.TaskBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

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
