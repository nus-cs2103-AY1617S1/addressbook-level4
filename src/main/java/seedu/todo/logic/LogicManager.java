package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.logic.commands.Command;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.logic.parser.Parser;
import seedu.todo.model.Model;
import seedu.todo.model.TodoList;
import seedu.todo.model.TodoModel;
import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final TodoModel todoModel;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.todoModel = new TodoList();
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
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<ImmutableTask> getObservableTaskList() {
        return todoModel.getObserveableList();
    }
}
