package seedu.todo.logic.commands;

import java.util.List;

import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.enumerations.TaskViewFilters;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

public class ViewCommand extends BaseCommand {
    
    private static final String VERB = "view is currently displayed";
    private Argument<String> view = new StringArgument("view").required();
                         
        

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
        if(!Arrays.asList(TaskViewFilters.values()).
                contains(view.getValue().toLowerCase())){
            String error = String.format("%s is not a valid view.", view.getValue());
            errors.put("view", error);
        }
        
            
    }

    @Override
    public CommandResult execute() throws ValidationException {
        
        
        return taskSuccessfulResult( view.getValue(), ViewCommand.VERB);
    }

}
