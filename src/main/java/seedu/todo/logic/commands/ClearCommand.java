package seedu.todo.logic.commands;

import java.util.List;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;


public class ClearCommand extends BaseCommand {
    

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[0];
    }

    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Clear tasks in current view", getCommandName()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        model.deleteAll();
        return new CommandResult("Tasks deleted");
        
    }

}
