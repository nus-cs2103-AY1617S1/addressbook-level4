package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.model.task.ImmutableTask;

public class CompleteCommand extends BaseCommand {
    private static final String VERB = "completed";

    private Argument<Integer> index = new IntArgument("index").required();

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{ index };
    }

    @Override
    public CommandResult execute() throws ValidationException {
        ImmutableTask task = this.model.update(index.getValue(), t -> t.setCompleted(!t.isCompleted()));
        return taskSuccessfulResult(task.getTitle(), CompleteCommand.VERB);
    }

}
