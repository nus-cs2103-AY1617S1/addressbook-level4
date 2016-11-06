package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.commons.events.ui.JumpToEventListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Description;
import seedu.task.model.item.Event;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.UniqueEventList;
import seedu.taskcommons.core.EventsCenter;
import seedu.taskcommons.core.LogsCenter;
import seedu.taskcommons.core.UnmodifiableObservableList;

//@@author A0127570H
/**
 * Adds an event to the task book.
 * @author kian ming
 */

public class AddEventCommand extends AddCommand {

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task book";

    private final Logger logger = LogsCenter.getLogger(AddEventCommand.class);
    private final Event toAddEvent;

    /**
     * Convenience constructor using raw values.

     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddEventCommand(String name, String description, String startDuration, String endDuration) throws IllegalValueException {
        
        if (description.isEmpty()) {
            this.toAddEvent = new Event(new Name(name), null, new EventDuration(startDuration, endDuration));
        } else {
            this.toAddEvent = new Event(new Name(name), new Description(description), new EventDuration(startDuration, endDuration));
        }
    }

    public AddEventCommand(ReadOnlyEvent event) {
    	this.toAddEvent = new Event(event);
	}

    /*
     * Execute method for add event command
     * Newly added event is to be selected for easy viewing
     * To post a JumpToEventListRequestEvent
     */
	@Override
    public CommandResult execute() {
        assert model != null;
        logger.info("-------[Executing AddEventCommand] " + this.toString() );
        try {
            model.addEvent(toAddEvent);
            
            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
            EventsCenter.getInstance().post(new JumpToEventListRequestEvent(toAddEvent, lastShownList.indexOf(toAddEvent)));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAddEvent));
        } catch (UniqueEventList.DuplicateEventException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        }

    }
	
	@Override
	public CommandResult undo() {
		DeleteEventCommand reverseCommand = new DeleteEventCommand(toAddEvent);
		reverseCommand.setData(model);
		
		return reverseCommand.execute();
	}
	
	
	public UndoableCommand prepareUndoCommand() {
		UndoableCommand command = new DeleteEventCommand(toAddEvent);
		
		command.setData(model);
		return command;
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD +" "+ this.toAddEvent.getAsText();
	}
}
