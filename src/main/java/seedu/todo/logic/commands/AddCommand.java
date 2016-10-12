package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

public class AddCommand extends BaseCommand {
    
    private Argument<String> title = new StringArgument("title").required();
    
    private Argument<String> description = new StringArgument("description")
            .flag("m");
    
    private Argument<Boolean> pin = new FlagArgument("pin")
            .flag("p");
    
    private Argument<String> location = new StringArgument("location")
            .flag("l");

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            title, description, location, pin,
        };
    }

    @Override
    public void execute() throws IllegalValueException, ValidationException {
        this.model.add(title.getValue(), task -> {
            task.setDescription(description.getValue());
            task.setPinned(pin.getValue());
            task.setLocation(location.getValue());
        });
    }

}
