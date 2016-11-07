package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.UniqueEventList.EventNotFoundException;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;

/**
 * @@author A0121608N
 * Deletes an Event identified using it's last displayed index from the taskbook.
 * 
 */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    
    private ReadOnlyEvent eventToDelete;
    
    private final Logger logger = LogsCenter.getLogger(DeleteEventCommand.class);
    
    public DeleteEventCommand(int targetIndex) {
        this.lastShownListIndex = targetIndex;
    }


    public DeleteEventCommand(Event eventToDelete) {
        this.eventToDelete = eventToDelete;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        
        if(eventToDelete == null){
            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
    
            if (outOfBounds(lastShownList.size())) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }
            
            eventToDelete = lastShownList.get(lastShownListIndex - 1);
        }
        
        logger.info("-------[Executing DeleteEventCommand] " + this.toString() );
        
        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException tnfe) {
            assert false : "The target event cannot be missing";
        }
       
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    private boolean outOfBounds(int listSize){
        return listSize < lastShownListIndex || lastShownListIndex < 1;
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
