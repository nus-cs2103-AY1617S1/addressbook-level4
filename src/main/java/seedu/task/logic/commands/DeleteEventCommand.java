package seedu.task.logic.commands;

import seedu.task.model.item.*;
import seedu.task.model.item.UniqueEventList.EventNotFoundException;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Deletes an Event identified using it's last displayed index from the address book.
 * @@author A0121608N
 */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    
    private ReadOnlyEvent eventToDelete;
    
    public DeleteEventCommand(int targetIndex) {
        this.lastShownListIndex = targetIndex;
    }
    
    public DeleteEventCommand(ReadOnlyEvent eventToDelete) {
		this.eventToDelete = eventToDelete;
	}


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (lastShownList.size() < lastShownListIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        
        if(lastShownListIndex != 0){
        	eventToDelete = lastShownList.get(lastShownListIndex - 1);
        }

        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException tnfe) {
            assert false : "The target event cannot be missing";
        }
       
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }


	@Override
	public CommandResult undo() {
		AddEventCommand reverseCommand = new AddEventCommand(eventToDelete);
		reverseCommand.setData(model);
		
		return reverseCommand.execute();
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD +" "+ this.eventToDelete.getAsText();
	}
}
