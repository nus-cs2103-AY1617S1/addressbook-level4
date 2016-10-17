package seedu.todo.logic.commands;

import java.util.List;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExpandCollapseTaskEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;

public class ShowCommand extends BaseCommand {
    
    private Argument<Integer> view = new IntArgument("view").required(); 

    @Override
    protected Parameter[] getArguments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCommandName() {
        // TODO Auto-generated method stub
        return "show";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        // TODO Auto-generated method stub
        return ImmutableList.of(new CommandSummary("Expands or collapses the details of a task", getCommandName(), getArgumentSummary()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        EventsCenter.getInstance().post(new ExpandCollapseTaskEvent(view.getValue())); 
        return new CommandResult(null);
    }

}
