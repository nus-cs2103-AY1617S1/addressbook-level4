package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;

import seedu.todo.commons.events.ui.HighlightTaskEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.task.ImmutableTask;

import java.util.List;

//@@author A0092382A
public class CompleteCommand extends BaseCommand {
    private static final String VERB_COMPLETE = "marked complete";
    private static final String VERB_INCOMPLETE = "marked incomplete";

    private Argument<Integer> index = new IntArgument("index");

    private Argument<String> updateAllFlag = new StringArgument("all").flag("all");

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[] { index, updateAllFlag };
    }

    @Override
    public String getCommandName() {
        return "complete";
    }

    @Override
    protected void validateArguments() {
        if (updateAllFlag.hasBoundValue() && index.hasBoundValue()) {
            errors.put("You must either specify an index or an /all flag, not both!");
        } else if (!index.hasBoundValue() && !updateAllFlag.hasBoundValue()) {
            errors.put("You must specify an index or a /all flag. You have specified none!");
        }
    }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Mark task as completed", getCommandName(), getArgumentSummary()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        if (index.hasBoundValue()) {
            ImmutableTask task = this.model.update(index.getValue(), t -> t.setCompleted(!t.isCompleted()));
            eventBus.post(new HighlightTaskEvent(task));
            String feedback = task.isCompleted() ? CompleteCommand.VERB_COMPLETE : CompleteCommand.VERB_INCOMPLETE;
            return taskSuccessfulResult(task.getTitle(), feedback);
        } else {
            this.model.updateAll(t -> t.setCompleted(true));
            return new CommandResult("All tasks marked as completed");
        }
    }

}
