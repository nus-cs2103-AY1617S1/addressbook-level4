package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.*;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final MasterParser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new MasterParser();
        
        registerAllDefaultCommandParsers();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parse(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    private void registerAllDefaultCommandParsers() {
        parser.registerCommandParser(new AddCommandParser());
        parser.registerCommandParser(new DeleteCommandParser());
        parser.registerCommandParser(new FindCommandParser());
    }
}
