package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.task.ImmutableTask;

public class EditCommand extends BaseCommand {
	
    private Argument<Integer> index = new IntArgument("index").required();
	
    
    private Argument<String> description = new StringArgument("description")
            .flag("m");
    
    private Argument<Boolean> pin = new FlagArgument("pin")
            .flag("p");
    
    private Argument<String> location = new StringArgument("location")
            .flag("l");

	@Override
	protected Parameter[] getArguments() {
		return new Parameter[] {index,description, pin, location};
	}

	@Override
	public void execute() throws IllegalValueException {
		ImmutableTask toEdit=this.getTaskAt(index.getValue());
		this.model.update(toEdit, task -> {task.setDescription(description.getValue());
            task.setPinned(pin.getValue());
            task.setLocation(location.getValue());
        });
	}

}
