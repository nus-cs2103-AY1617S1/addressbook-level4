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
    
    private Argument<Integer> index = new IntArgument("index").required(); 

    @Override
    protected Parameter[] getArguments() {
        // TODO Auto-generated method stub
        return new Parameter[] { index };
    }

    @Override
    public String getCommandName() {
        // TODO Auto-generated method stub
        return "show";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        // TODO Auto-generated method stub
        return ImmutableList.of(new CommandSummary("Shows details of a task", getCommandName(), getArgumentSummary()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        EventsCenter.getInstance().post(new ExpandCollapseTaskEvent(index.getValue())); 
        return new CommandResult("Command executed.");
    }

}
