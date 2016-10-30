package seedu.todo.logic.commands;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import seedu.todo.commons.util.StringUtil;
import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.commons.events.ui.ExpandCollapseTaskEvent;
import seedu.todo.commons.events.ui.HighlightTaskEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.*;
import seedu.todo.model.tag.UniqueTagCollectionValidator;
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

    private Argument<String> tags = new StringArgument("tag")
            .flag("t");

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            title, date, description, location, pin, tags
        };
    }

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        String eventArguments = Joiner.on(" ").join(title, "/d start and end time", description, location, pin, "/t tag1 [, tag2, ...]");
        
        return ImmutableList.of(
            new CommandSummary("Add task", getCommandName(), getArgumentSummary()), 
            new CommandSummary("Add event", getCommandName(), eventArguments));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        ImmutableTask addedTask = this.model.add(title.getValue(), task -> {
            task.setDescription(description.getValue());
            task.setPinned(pin.getValue());
            task.setLocation(location.getValue());
            task.setStartTime(date.getValue().getStartTime());
            task.setEndTime(date.getValue().getEndTime());
            model.addTagsToTask(task, StringUtil.splitString(tags.getValue()));
        });
        if(!model.getObservableList().contains(addedTask)) {
            model.view(TaskViewFilter.DEFAULT);
        }

        eventBus.post(new HighlightTaskEvent(addedTask));
        eventBus.post(new ExpandCollapseTaskEvent(addedTask));
        return taskSuccessfulResult(title.getValue(), AddCommand.VERB);
    }

    //@@author A0135803H
    @Override
    protected void validateArguments() {
        //Validate Tags
        String[] tagNames = StringUtil.splitString(tags.getValue());
        UniqueTagCollectionValidator validator = new UniqueTagCollectionValidator(tags.getName(), errors);
        validator.validateAddTags(tagNames);
    }
}
