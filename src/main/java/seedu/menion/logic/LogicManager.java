package seedu.menion.logic;

import javafx.collections.ObservableList;
import seedu.menion.commons.core.ComponentManager;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.logic.commands.Command;
import seedu.menion.logic.commands.CommandResult;
import seedu.menion.logic.parser.ActivityParser;
import seedu.menion.model.Model;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final ActivityParser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new ActivityParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }
    
    //@@author A0146752B
    @Override
    public ObservableList<ReadOnlyActivity> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyActivity> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyActivity> getFilteredEventList() {
        return model.getFilteredEventList();
    }
    
}
