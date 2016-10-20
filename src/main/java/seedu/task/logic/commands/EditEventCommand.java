package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Description;
import seedu.task.model.item.Event;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Executes editing of events according to the input argument.
 * @author kian ming
 */
public class EditEventCommand extends EditCommand {

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task book";
    
    private Name newName;
    private Description newDescription;
    private EventDuration newDuration;
    
    private boolean isNameToBeEdit;
    private boolean isDescriptionToBeEdit;
    private boolean isDurationToBeEdit;
    
    /**
     * Convenience constructor using raw values.
     * Only fields to be edited will have values parsed in.
     * Empty string parsed in otherwise.
     *  
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public EditEventCommand(Integer index,
                           boolean isNameToBeEdited, String name, 
                           boolean isDescriptionToBeEdited, String description,
                           boolean isDurationToBeEdited, String duration
                           ) throws IllegalValueException {
        
        setTargetIndex(index);
        this.isNameToBeEdit = isNameToBeEdited;
        this.isDescriptionToBeEdit = isDescriptionToBeEdited;
        this.isDurationToBeEdit = isDurationToBeEdited;
        
        newName = null;
        newDescription = null;
        newDuration = null;
        
        if (isNameToBeEdited) {
            assert name != null && name != "";
            newName = new Name(name);
        } 
        if (isDescriptionToBeEdited) {
            assert description != null && description != "";
            newDescription = new Description(description);
        }
        if (isDurationToBeEdited) {
            assert duration != null && duration != "";
            newDuration = new EventDuration(duration);
        }
    }
    
    /**
     * Gets the event to be edited based on the index.
     * Only fields to be edited will have values updated.
     * @throws DuplicateTaskException 
     */
    @Override
    public CommandResult execute() {
        try {
            
            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();        
            ReadOnlyEvent targetEvent = lastShownList.get(getTargetIndex());
            
            Event editEvent = editEvent(targetEvent);
            model.editEvent(editEvent, targetEvent);
            
            return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editEvent));

        } catch (UniqueEventList.DuplicateEventException e) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        } catch (IndexOutOfBoundsException ie) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Edits the necessary fields.
     * @return event that has the fields according to edit requirements.
     */
    private Event editEvent(ReadOnlyEvent targetEvent) {
        assert targetEvent != null;
        if (!isNameToBeEdit) {
            newName = targetEvent.getEvent();
        }
        if (!isDescriptionToBeEdit) {
            newDescription = targetEvent.getDescription();
        }
        if (!isDurationToBeEdit) {
            newDuration = targetEvent.getDuration();
        }
        return new Event (this.newName, this.newDescription, this.newDuration);
    }

}