package seedu.todo.logic.commands;

import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;

public class AddTodoCommand extends BaseCommand {
    
    private Argument<String> name = new StringArgument("name").required();
    private Argument<String> description = new StringArgument("description")
            .flag("m");
    private Argument<Boolean> pin = new FlagArgument("pin")
            .flag("p");

    @Override
    public Parameter[] getArguments() {
        return new Parameter[] {
            name, description, pin,
        };
    }

    @Override
    public void execute() {
        // TODO: Complete this command
    }

}
