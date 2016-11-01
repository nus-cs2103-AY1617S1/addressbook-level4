package seedu.agendum.logic;

import javafx.collections.ObservableList;
import seedu.agendum.commons.core.ComponentManager;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.logic.commands.AliasCommand;
import seedu.agendum.logic.commands.Command;
import seedu.agendum.logic.commands.CommandLibrary;
import seedu.agendum.logic.commands.CommandResult;
import seedu.agendum.logic.commands.UnaliasCommand;
import seedu.agendum.logic.parser.Parser;
import seedu.agendum.model.Model;
import seedu.agendum.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private CommandLibrary commandLibrary;

    public LogicManager(Model model) {
        this.model = model;
        this.commandLibrary = CommandLibrary.getInstance();
        this.parser = new Parser(this.commandLibrary);
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);

        if (command instanceof AliasCommand) {
            ((AliasCommand) command).setData(model, commandLibrary);
        } else if (command instanceof UnaliasCommand) {
            ((UnaliasCommand) command).setData(model, commandLibrary);
        } else {
            command.setData(model);
        }
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

}
