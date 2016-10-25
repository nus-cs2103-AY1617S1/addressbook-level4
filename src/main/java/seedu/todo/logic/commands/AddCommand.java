package seedu.todo.logic.commands;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import seedu.todo.commons.core.TaskViewFilter;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.*;
import seedu.todo.model.task.ImmutableTask;

import java.util.List;

//@@author A0135817B
public class AddCommand extends BaseCommand {
    private static final String VERB = "added";
    
    private Argument<String> title = new StringArgument("title").required();
    
    private Argument<String> description = new StringArgument("description")
            .flag("m");
    
    private Argument<Boolean> pin = new FlagArgument("pin")
            .flag("p");
    
    private Argument<String> location = new StringArgument("location")
            .flag("l");
    
    private Argument<DateRange> date = new DateRangeArgument("deadline")
            .flag("d");

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            title, date, description, location, pin,
        };
    }

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        String eventArguments = Joiner.on(" ").join(title, "/d start and end time", description, location, pin);
        
        return ImmutableList.of(
            new CommandSummary("Add task", getCommandName(), getArgumentSummary()), 
            new CommandSummary("Add event", getCommandName(), eventArguments));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        TaskViewFilter currentView = model.getViewFilter().get();
        ImmutableTask addedTask = this.model.add(title.getValue(), task -> {
            task.setDescription(description.getValue());
            task.setPinned(pin.getValue());
            task.setLocation(location.getValue());
            task.setStartTime(date.getValue().getStartTime());
            task.setEndTime(date.getValue().getEndTime());
        });
        if(!currentView.filter.test(addedTask)) {
            model.view(TaskViewFilter.DEFAULT);
        }
        return taskSuccessfulResult(title.getValue(), AddCommand.VERB);
    }

}
