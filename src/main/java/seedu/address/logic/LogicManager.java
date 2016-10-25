package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.task.TaskComponent;
import seedu.address.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private URManager urManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
        this.urManager = new URManager();
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
    public ObservableList<TaskComponent> getFilteredTaskList() {
        return model.getFilteredTaskComponentList();
    }   
    
}
