package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

import java.util.List;

public class LoadCommand extends BaseCommand {
    Argument<String> location = new StringArgument("location").required();
    
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{ location };
    }

    @Override
    public String getCommandName() {
        return "load";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Load todo list from file", getCommandName(), 
            getArgumentSummary()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        String path = location.getValue();
        model.load(path);
        return new CommandResult(String.format("File loaded from %s", path));
    }
}
