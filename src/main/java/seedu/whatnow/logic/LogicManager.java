//@@author A0139772U
package seedu.whatnow.logic;

import javafx.collections.ObservableList;
import seedu.whatnow.commons.core.ComponentManager;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.logic.commands.Command;
import seedu.whatnow.logic.commands.CommandResult;
import seedu.whatnow.logic.parser.Parser;
import seedu.whatnow.model.Model;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.whatnow.storage.Storage;

import java.text.ParseException;
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
    public CommandResult execute(String commandText) throws ParseException, DuplicateTaskException, TaskNotFoundException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredScheduleList() {
        return model.getFilteredScheduleList();
    }
}
