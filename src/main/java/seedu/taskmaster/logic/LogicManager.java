package seedu.taskmaster.logic;

import javafx.collections.ObservableList;
import seedu.taskmaster.commons.core.ComponentManager;
import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.logic.commands.Command;
import seedu.taskmaster.logic.commands.CommandResult;
import seedu.taskmaster.logic.parser.Parser;
import seedu.taskmaster.model.Model;
import seedu.taskmaster.model.task.TaskOccurrence;
import seedu.taskmaster.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private UndoRedoManager urManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
        this.urManager = new UndoRedoManager();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        urManager.addToUndoQueue(model, command);
        command.assignManager(urManager);
        return command.execute();
    }

    @Override
    public ObservableList<TaskOccurrence> getFilteredTaskList() {
        return model.getFilteredTaskComponentList();
    }
    
    @Override 
    public void initializeUndoRedoManager(){
        urManager.resetQueue();
    }

}
