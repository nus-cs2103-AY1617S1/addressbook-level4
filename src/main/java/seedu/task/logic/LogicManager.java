package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.parser.Parser;
import seedu.task.model.Model;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.storage.Storage;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public static Stack<Command> operations = new Stack<Command>();
    public static Stack<Task> tasks = new Stack<Task>();
    public static Stack<Integer> indexes = new Stack<Integer>();

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        CommandResult result = command.execute();

        if(command.undoCanOrNot())
        	this.operations.push(command);

        return result;
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    public static CommandResult undo() throws IllegalValueException {
    	try{
    		return operations.pop().undo();
    	}catch (EmptyStackException e){
    		return new CommandResult("No operations can be undone.");
    	}
    }
}
