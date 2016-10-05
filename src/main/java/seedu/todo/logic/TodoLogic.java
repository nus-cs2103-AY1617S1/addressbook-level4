package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.logic.parser.TodoParser;
import seedu.todo.model.TodoList;
import seedu.todo.model.TodoModel;
import seedu.todo.model.task.ImmutableTask;

/**
 * Central controller for the application, abstracting application logic from the UI
 */
public class TodoLogic {
    
    private final TodoModel model = new TodoList();
    private final TodoDispatcher dispatcher = new TodoDispatcher();
    
    public void execute(String input) throws IllegalValueException {
        ParseResult parser = new TodoParser(input);
        BaseCommand command = dispatcher.dispatch(parser);
        command.execute();
    }
    
    public ObservableList<ImmutableTask> getObservableTaskList() {
        return model.getObserveableList();
    }
    
    
}
