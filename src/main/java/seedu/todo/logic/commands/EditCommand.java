package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.*;
import seedu.todo.model.task.ImmutableTask;

public class EditCommand extends BaseCommand {
    private static final String VERB = "edited";
    
    private Argument<Integer> index = new IntArgument("index").required();
    
    private Argument<String> description = new StringArgument("description")
            .flag("m");
    
    private Argument<Boolean> pin = new FlagArgument("pin")
            .flag("p");
    
    private Argument<String> location = new StringArgument("location")
            .flag("l");

    private Argument<DateRange> date = new DateRangeArgument("date")
            .flag("d");

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[] { index, date, description, pin, location };
    }

    @Override
    public CommandResult execute() throws IllegalValueException {
        ImmutableTask toEdit = this.getTaskAt(index.getValue());
        
        this.model.update(toEdit, task -> {
            if (description.hasBoundValue()) {
                task.setDescription(description.getValue());
            }
            
            if (pin.hasBoundValue()) {
                task.setPinned(pin.getValue());
            }
            
            if (location.hasBoundValue()) {
                task.setLocation(location.getValue());
            }
            
            if (date.hasBoundValue()) {
                task.setStartTime(date.getValue().getStartTime());
                task.setEndTime(date.getValue().getEndTime());
            }
        });
        
        return taskSuccessfulResult(toEdit.getTitle(), EditCommand.VERB);
    }

}
