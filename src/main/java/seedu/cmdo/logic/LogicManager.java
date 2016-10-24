package seedu.cmdo.logic;

import javafx.collections.ObservableList;
import seedu.cmdo.commons.core.ComponentManager;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.logic.commands.Command;
import seedu.cmdo.logic.commands.CommandResult;
import seedu.cmdo.logic.parser.Blocker;
import seedu.cmdo.logic.parser.MainParser;
import seedu.cmdo.model.Model;
import seedu.cmdo.model.StatusSaver;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.storage.Storage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final MainParser parser;
    private final StatusSaver statusSaver;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = MainParser.getInstance();
        this.statusSaver = new StatusSaver();

    }
    
    @Override	
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        command.setStatusSaver(statusSaver);
        return command.execute();
    }
    
    public UnmodifiableObservableList<ReadOnlyTask> getBlockedList() {
    	return model.getBlockedList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList(boolean firstRun) {
        return model.getFilteredTaskList(firstRun);
    }
}