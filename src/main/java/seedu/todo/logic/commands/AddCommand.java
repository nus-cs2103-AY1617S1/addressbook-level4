package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.*;

public class AddCommand extends BaseCommand {
    private static final String VERB = "added";
    
    private Argument<String> title = new StringArgument("title").required();
    
    private Argument<String> description = new StringArgument("description")
            .flag("m");
    
    private Argument<Boolean> pin = new FlagArgument("pin")
            .flag("p");
    
    private Argument<String> location = new StringArgument("location")
            .flag("l");
    
    private Argument<DateRange> date = new DateRangeArgument("date")
            .flag("d");

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            title, date, description, location, pin,
        };
    }

    @Override
    public CommandResult execute() throws IllegalValueException {
        this.model.add(title.getValue(), task -> {
            task.setDescription(description.getValue());
            task.setPinned(pin.getValue());
            task.setLocation(location.getValue());
            task.setStartTime(date.getValue().getStartTime());
            task.setEndTime(date.getValue().getEndTime());
        });
        
        return taskSuccessfulResult(title.getValue(), AddCommand.VERB);
    }

}
