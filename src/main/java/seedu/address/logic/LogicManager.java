package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandFactory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.SequentialParser;
import seedu.address.model.Model;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.storage.Storage;

import java.util.logging.Logger;

/**
 * Underlying logic in application
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandFactory commandFactory;
    {
        commandFactory = new CommandFactory();
    }

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("User command: " + commandText + "");

        Command command = commandFactory.build(commandText);

        return command.execute(model, eventsCenter);
    }

    @Override
    public ObservableList<ReadOnlyToDo> getFilteredToDoList() {
        return model.getFilteredToDoList();
    }
}
