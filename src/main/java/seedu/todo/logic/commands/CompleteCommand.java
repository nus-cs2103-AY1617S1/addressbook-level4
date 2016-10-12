package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
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
    public CommandResult execute() throws IllegalValueException {
        ImmutableTask toComplete = this.getTaskAt(index.getValue());
        boolean result = !toComplete.isCompleted();
        this.model.update(toComplete, task-> task.setCompleted(result));
        
        return taskSuccessfulResult(toComplete.getTitle(), CompleteCommand.VERB);
    }

}
