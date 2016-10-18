package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;

import java.util.List;

//@@author A0135817B
public class RedoCommand extends BaseCommand {
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[0];
    }

    @Override
    public String getCommandName() {
        return "redo";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Redo", getCommandName()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        model.redo();
        return new CommandResult("Redid last action");
    }
}
