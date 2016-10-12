package seedu.todo.logic.commands;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.logic.arguments.Parameter;

/**
 * Terminates the program.
 */
public class ExitCommand extends BaseCommand {
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return null;
    }

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{};
    }

}
