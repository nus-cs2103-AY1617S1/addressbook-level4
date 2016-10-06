package seedu.todo.logic;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.AddTodoCommand;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.parser.ParseResult;

/**
 * Selects the correct command based on the parser results
 */
public class TodoDispatcher implements Dispatcher {
    public BaseCommand dispatch(ParseResult parser) throws IllegalValueException {
        BaseCommand command;
        
        switch (parser.getComand()) {
        case "add":
        case "a":
            command = new AddTodoCommand();
            break;
        default:
            return null;
        }
        
        command.setArguments(parser);
        
        return command;
    }
}
