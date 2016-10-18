package seedu.todo.logic.commands;

import java.util.List;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.enumerations.TaskViewFilters;
import seedu.todo.commons.events.ui.ChangeViewRequestEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

public class ViewCommand extends BaseCommand {
    
    private static final String VERB = "view is currently displayed";
    private Argument<String> view = new StringArgument("view").required();
    private TaskViewFilters viewSpecified;
                         
        

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
        return ImmutableList.of(new CommandSummary("View tasks of particular tab", 
                                getCommandName(), getArgumentSummary()));
    }
    
    @Override
    protected void validateArguments(){
        TaskViewFilters[] viewArray = TaskViewFilters.values();
        String viewSpecified = view.getValue().trim().toLowerCase();
        for (int i=0; i<viewArray.length; i++){
            if(viewArray[i].getViewName().contentEquals(viewSpecified)){
                this.viewSpecified = viewArray[i];
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
        return taskSuccessfulResult( viewSpecified.toString(), ViewCommand.VERB);
    }

}
