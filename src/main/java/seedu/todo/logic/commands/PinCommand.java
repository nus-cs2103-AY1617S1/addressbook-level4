package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.model.task.ImmutableTask;

public class PinCommand extends BaseCommand {

    private Argument<Integer> index = new IntArgument("index").required();

    @Override
    protected Parameter[] getArguments() {
        return new Parameter[]{ index };
    }

    @Override
    public void execute() throws IllegalValueException {
        ImmutableTask toPin = this.getTaskAt(index.getValue());
        boolean isPinned = !toPin.isPinned();
        this.model.update(toPin, task->task.setPinned(isPinned));
    }

}