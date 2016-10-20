package seedu.task.logic.commands;

import seedu.task.model.item.*;
import seedu.task.model.item.UniqueEventList.EventNotFoundException;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Deletes an Event identified using it's last displayed index from the address book.
 * @author Tiankai
 */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    public DeleteEventCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException tnfe) {
            assert false : "The target event cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

}
