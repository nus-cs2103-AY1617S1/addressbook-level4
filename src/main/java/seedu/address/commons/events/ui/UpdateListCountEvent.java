package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Model;

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
