package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.history.UndoableCommandHistory;
import seedu.address.history.UndoableCommandHistoryManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.CommandParser;
import seedu.address.model.Model;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final UndoableCommandHistory history;
    private final CommandParser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.history = UndoableCommandHistoryManager.getInstance();
        this.parser = new CommandParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, history);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredUndoneTaskList() {
        return model.getFilteredUndoneTaskList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredDoneTaskList() {
        return model.getFilteredDoneTaskList();
    }

    /**
     * Generates the tool tip for the given user input.<br>
     * Asserts that the userInput is not null.
     * 
     * @param userInput The user input String
     * @return The tooltip appropriate for the given user input String
     */
    @Override
    public String generateToolTip(String userInput) {
        assert userInput != null;

        boolean viewingDoneList = model.isCurrentListDoneList();
        return parser.parseForTooltip(userInput, viewingDoneList);
    }
}
