package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.todo.ReadOnlyToDo;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public final int toDoIndex;

    public DeleteCommand(int toDoIndex) {
        this.toDoIndex = toDoIndex;
    }

    @Override
    public CommandResult execute(Model model, EventsCenter eventsCenter) {
        UnmodifiableObservableList<ReadOnlyToDo> lastShownList = model.getFilteredToDoList();

        if (lastShownList.size() < toDoIndex) {
            return new CommandResult(String.format(Messages.MESSAGE_TODO_ITEM_INDEX_INVALID, toDoIndex));
        }

        ReadOnlyToDo toDoToDelete = lastShownList.get(toDoIndex - 1);

        try {
            model.deleteToDo(toDoToDelete);
        } catch (IllegalValueException exception) {
            assert false : "The to-do at index shouldn't be invalid";
        }

        return new CommandResult(String.format(Messages.MESSAGE_TODO_DELETED, toDoToDelete.toString()));
    }

}
