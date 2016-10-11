package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.todo.ReadOnlyToDo;

/**
 * Marks a to-do item as done
 */
public class FinishCommand extends Command {

    public static final String COMMAND_WORD = "finish";

    public final int toDoIndex;

    public FinishCommand(int toDoIndex) {
        this.toDoIndex = toDoIndex;
    }

    @Override
    public CommandResult execute(Model model, EventsCenter eventsCenter) {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyToDo> lastShownList = model.getFilteredToDoList();

        if (lastShownList.size() < toDoIndex) {
            return new CommandResult(String.format(Messages.MESSAGE_TODO_ITEM_INDEX_INVALID, toDoIndex), true);
        }

        ReadOnlyToDo toDoToFinish = lastShownList.get(toDoIndex - 1);

        try {
            model.deleteToDo(toDoToFinish);
        } catch (IllegalValueException exception) {
            return new CommandResult(exception.getMessage(), true);
        }

        return new CommandResult(String.format(Messages.MESSAGE_TODO_FINISHED, toDoToFinish.getTitle().toString()));
    }

}
