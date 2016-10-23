package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;

import java.util.List;

//@@author A0135817B
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

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Close this app :(", getCommandName()));
    }

}
