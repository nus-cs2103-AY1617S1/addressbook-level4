package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

import java.util.List;

//@@author A0135817B
/**
 * Saves the save file to a different location 
 */
public class SaveCommand extends BaseCommand {
    private Argument<String> location = new StringArgument("location");
    
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{ location };
    }

    @Override
    public String getCommandName() {
        return "save";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(
            new CommandSummary("Save to a different file", getCommandName(), "location"), 
            new CommandSummary("Find my todo list file", getCommandName()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        if (location.hasBoundValue()) {
            model.save(location.getValue());
            
            return new CommandResult(String.format("Todo list saved successfully to %s", location));
        } else {
            return new CommandResult(String.format("Save location: %s", model.getStorageLocation()));
        }
    }
}
