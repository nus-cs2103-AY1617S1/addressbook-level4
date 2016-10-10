package seedu.todo.logic;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.BaseCommand;
import seedu.todo.logic.commands.CompleteCommand;
import seedu.todo.logic.commands.AddCommand;
import seedu.todo.logic.commands.DeleteCommand;
import seedu.todo.logic.commands.ExitCommand;
import seedu.todo.logic.commands.PinCommand;
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
            command = new AddCommand();
            break;
        case "complete":
        case "comp":
        case "c":
        	command= new CompleteCommand();
        	break;
        case "delete":
        case "d":
            command = new DeleteCommand();
            break;
        

        case "exit":
        case "e":
            command = new ExitCommand();
            break;
        case "pin":
        case "p":
            command = new PinCommand();
            break;
        default:
            throw new IllegalValueException("Command not recognized");
        }
        
        return command;
    }
}
