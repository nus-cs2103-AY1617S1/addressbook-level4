package seedu.address.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Description;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager_Task of the app.
 */
public class LogicManager_Task extends ComponentManager implements Logic_Task {
    private final Logger logger = LogsCenter.getLogger(LogicManager_Task.class);

    private final Model model;
    private final Parser parser;

    public LogicManager_Task(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
    	ObservableList<Task> stubList = FXCollections.observableArrayList();
    	Description a = new Description("Testing A");
    	Description b = new Description("Testing B");
    	Description c = new Description("Testing C");
    	stubList.add(new FloatingTask(a));
    	stubList.add(new FloatingTask(b));
    	stubList.add(new FloatingTask(c));
    	
        return stubList;
    }
}
