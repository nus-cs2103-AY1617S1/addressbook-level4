package seedu.todo.logic.commands;

import java.util.List;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.enumerations.TaskViewFilter;
import seedu.todo.commons.events.ui.ChangeViewRequestEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

public class ViewCommand extends BaseCommand {
    private static final String FEEDBACK_FORMAT = "Displaying %s view";
    
    private Argument<String> view = new StringArgument("view").required();
    
    private TaskViewFilter viewSpecified;
    
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{ view };
    }

    @Override
    public String getCommandName() {
        return "view";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Switch tabs", getCommandName(), getArgumentSummary()));
    }
    
    @Override
    protected void validateArguments(){
        TaskViewFilter[] viewArray = TaskViewFilter.values();
        String viewSpecified = view.getValue().trim().toLowerCase();
        
        for (TaskViewFilter aViewArray : viewArray) {
            if (aViewArray.getViewName().contentEquals(viewSpecified)) {
                this.viewSpecified = aViewArray;
                return;
            }
        }
        
        String error = String.format("The view %s does not exist", view.getValue());
        errors.put("view", error);
    }

    @Override
    public CommandResult execute() throws ValidationException {
        model.view(viewSpecified.getFilter(), null);
        EventsCenter.getInstance().post(new ChangeViewRequestEvent(viewSpecified));
        String feedback = String.format(ViewCommand.FEEDBACK_FORMAT, viewSpecified);
        return new CommandResult(feedback);
    }

}
