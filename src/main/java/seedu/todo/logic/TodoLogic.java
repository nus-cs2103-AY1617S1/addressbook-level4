package seedu.todo.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.logic.parser.Parser;
import seedu.todo.model.TodoModel;
import seedu.todo.model.task.ImmutableTask;

/**
 * Central controller for the application, abstracting application logic from the UI
 */
public class TodoLogic implements Logic {
    private final Parser parser;
    private final TodoModel model;
    private final Dispatcher dispatcher;
    
    private static final Logger logger = LogsCenter.getLogger(TodoLogic.class);
    
    public TodoLogic(Parser parser, TodoModel model, Dispatcher dispatcher) {
        assert parser != null;
        assert model != null;
        assert dispatcher != null;
        
        this.parser = parser;
        this.model = model;
        this.dispatcher = dispatcher;
    }
    
    public void execute(String input) {
        ParseResult parseResult = parser.parse(input);
        logger.info("Parsed command: " + parseResult.toString());
        
        try {
            BaseCommand command = dispatcher.dispatch(parseResult);
            command.setArguments(parseResult);
            command.setModel(model);
            command.execute();
        } catch (IllegalValueException e) {
            // TODO: Do something about incorrect input
            logger.info(e.getMessage());
        } catch (ValidationException e) {
            // TODO: Do something about incorrect input
            logger.info(e.getMessage());
        }
    }
    
    public ObservableList<ImmutableTask> getObservableTaskList() {
        return model.getObserveableList();
    }
    
    
}
