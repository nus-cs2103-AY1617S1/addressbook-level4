package seedu.tasklist.logic;

import javafx.collections.ObservableList;
import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.CommandResult;
import seedu.tasklist.logic.parser.Parser;
import seedu.tasklist.model.Model;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.TaskCounter;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.storage.Storage;

import java.io.IOException;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

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
    public CommandResult execute(String commandText) throws IOException, JSONException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
    	model.updateFilteredListToShowIncomplete();
        return model.getFilteredTaskList();
    }
    
    @Override
    public ReadOnlyTaskList getTaskList() {
    	return model.getTaskList();
    }
    
    @Override
    public TaskCounter getTaskCounter() {
    	return model.getTaskCounter();
    }
}
