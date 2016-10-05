package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.logic.parser.ParserContract;
import seedu.todo.model.TodoModel;
import seedu.todo.model.task.ImmutableTask;

/**
 * Central controller for the application, abstracting application logic from the UI
 */
public class TodoLogic {
    private final ParserContract parser;
    private final TodoModel model;
    private final Dispatcher dispatcher;
    
    public TodoLogic(ParserContract parser, TodoModel model, Dispatcher dispatcher) {
        this.parser = parser;
        this.model = model;
        this.dispatcher = dispatcher;
    }
    
    public void execute(String input) throws IllegalValueException {
        ParseResult parseResult = parser.parse(input);
        BaseCommand command = dispatcher.dispatch(parseResult);
        command.execute();
    }
    
    public ObservableList<ImmutableTask> getObservableTaskList() {
        return model.getObserveableList();
    }
    
    
}
