package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;

import java.util.List;

//@@author A0135817B
public class UndoCommand extends BaseCommand {
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[0];
    }

    @Override
    public String getCommandName() {
        return "undo";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Undo last edit", getCommandName()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        model.undo();
        return new CommandResult("Undid last action");
    }
}
