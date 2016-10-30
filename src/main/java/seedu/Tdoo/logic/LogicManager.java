package seedu.Tdoo.logic;

import javafx.collections.ObservableList;
import seedu.Tdoo.commons.core.ComponentManager;
import seedu.Tdoo.commons.core.LogsCenter;
import seedu.Tdoo.logic.commands.Command;
import seedu.Tdoo.logic.commands.CommandResult;
import seedu.Tdoo.logic.parser.Parser;
import seedu.Tdoo.model.Model;
import seedu.Tdoo.model.task.ReadOnlyTask;
import seedu.Tdoo.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, storage);
        return command.execute();
    }

    //@@author A0144061U
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTodoList() {
        return model.getFilteredTodoList();
    }
    
    //@@author A0144061U
    @Override
    public ObservableList<ReadOnlyTask> getFilteredEventList() {
        return model.getFilteredEventList();
    }
    
    //@@author A0144061U
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return model.getFilteredDeadlineList();
    }
}