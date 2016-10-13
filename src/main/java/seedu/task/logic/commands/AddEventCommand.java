package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Description;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Event;
import seedu.task.model.item.Name;
import seedu.task.model.item.UniqueEventList;

/**
 * Adds an event to the task book.
 * @author kian ming
 */

public class AddEventCommand extends AddCommand {

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task book";

    private final Event toAddEvent;

    /**
     * Convenience constructor using raw values.

     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddEventCommand(String name, String description, String duration) throws IllegalValueException {
        this.toAddEvent = new Event(new Name(name), new Description(description), new EventDuration(duration)); //TODO: more flexible of events type
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addEvent(toAddEvent);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAddEvent));
        } catch (UniqueEventList.DuplicateEventException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        }

    }
    
}
