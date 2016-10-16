package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.model.task.ImmutableTask;

import java.util.List;

public class CompleteCommand extends BaseCommand {
    private static final String VERB_COMPLETE = "marked complete";
    private static final String VERB_INCOMPLETE = "marked incomplete";
    

    private Argument<Integer> index = new IntArgument("index").required();

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{ index };
    }

    @Override
    public String getCommandName() {
        return "complete";
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Mark task as completed", getCommandName(), 
            getArgumentSummary()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        ImmutableTask task = this.model.update(index.getValue(), t -> t.setCompleted(!t.isCompleted()));
        String feedback = task.isCompleted() ? CompleteCommand.VERB_COMPLETE : CompleteCommand.VERB_INCOMPLETE;
        return taskSuccessfulResult(task.getTitle(), feedback);
    }

}
