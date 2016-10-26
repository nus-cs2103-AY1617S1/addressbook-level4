package seedu.oneline.logic;

import javafx.collections.ObservableList;
import seedu.oneline.commons.core.ComponentManager;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.logic.commands.Command;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.Model;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.tag.Tag; 
import seedu.oneline.storage.Storage;

import java.util.logging.Logger;

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
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        return model.executeCommand(command);
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<Tag> getTagList() {
        return model.getTagList(); 
    }
}
