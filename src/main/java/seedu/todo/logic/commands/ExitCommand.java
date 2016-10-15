package seedu.todo.logic.commands;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;

/**
 * Terminates the program.
 */
public class ExitCommand extends BaseCommand {
    private final static String EXIT_MESSAGE = "Goodbye!";
    
    @Override
    public CommandResult execute() throws ValidationException {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(ExitCommand.EXIT_MESSAGE);
    }

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{};
    }

}
