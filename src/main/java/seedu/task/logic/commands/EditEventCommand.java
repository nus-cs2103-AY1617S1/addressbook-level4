package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Description;
import seedu.task.model.item.Event;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Executes editing of events according to the input argument.
 * 
 * @author kian ming
 */

//@@author A0127570H
public class EditEventCommand extends EditCommand {

	public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
	public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task book";

	private Name newName;
	private Description newDescription;
	private EventDuration newDuration;
	private String newStartDuration;
	private String newEndDuration;

	private Event editEvent;
	private ReadOnlyEvent targetEvent;

	/**
	 * Convenience constructor using raw values. Only fields to be edited will
	 * have values parsed in. Empty string parsed in otherwise.
	 * Gets the event to be edited based on the index. Only fields to be edited
     * will have values updated.
	 * 
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public EditEventCommand(Integer index, String name, String description, 
	        String startDuration, String endDuration) throws IllegalValueException, IndexOutOfBoundsException {

		setTargetIndex(index);
		newStartDuration = startDuration;
		newEndDuration = endDuration;

		if (!name.isEmpty()) {
			newName = new Name(name);
		}
		if (!description.isEmpty()) {
			newDescription = new Description(description);
		}
		        
	} 

	/**
	 * Executes the editing of the event
	 * 
	 * @throws DuplicateTaskException
	 */
	@Override
	public CommandResult execute() {
		try {
		    UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();	        
	        targetEvent = lastShownList.get(getTargetIndex());
	        editEvent = editEvent(targetEvent); 		    
		    model.editEvent(editEvent, targetEvent);

			return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editEvent));

		} catch (UniqueEventList.DuplicateEventException e) {
			return new CommandResult(MESSAGE_DUPLICATE_EVENT);
		} catch (IndexOutOfBoundsException ie) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
		} catch (IllegalValueException e) {
		    indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        } 
	}

	/**
	 * Edits the necessary fields.
	 * 
	 * @return event that has the fields according to edit requirements.
	 * @throws IllegalValueException 
	 */
	private Event editEvent(ReadOnlyEvent targetEvent) throws IllegalValueException {
		assert targetEvent != null;
		if (newName == null) {
			newName = targetEvent.getEvent();
		}
		if (newDescription == null) {
			newDescription = targetEvent.getDescription().orElse(null);
		}
		if (newStartDuration.isEmpty() && newEndDuration.isEmpty()) {
			newDuration = targetEvent.getDuration();
		} else {
		    if (newStartDuration.isEmpty()) {
		        newDuration = new EventDuration (targetEvent.getDuration().getStartTimeAsText(), newEndDuration);
		    } else if (newEndDuration.isEmpty()){
		        newDuration = new EventDuration (newStartDuration, targetEvent.getDuration().getEndTimeAsText());
		    } else {
                newDuration = new EventDuration (newStartDuration, newEndDuration);
            }
		}
		return new Event(this.newName, this.newDescription, this.newDuration);
	}

	@Override
	public CommandResult undo() {
		try {
			model.editEvent((Event)targetEvent, editEvent);

			return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editEvent));
		} catch (UniqueEventList.DuplicateEventException e) {
			return new CommandResult(MESSAGE_DUPLICATE_EVENT);
		} catch (IndexOutOfBoundsException ie) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
		}
	}

}