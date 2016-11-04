package seedu.stask.commons.events.ui;

import seedu.stask.commons.events.BaseEvent;
import seedu.stask.model.Model;

//@@author A0143884W
/**
 * Indicates a request to update the list count
 */
public class UpdateListCountEvent extends BaseEvent {

	public final Model model; 
	
	public UpdateListCountEvent(Model model){
		this.model = model;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
