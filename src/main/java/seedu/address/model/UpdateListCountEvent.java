package seedu.address.model;

import seedu.address.commons.events.BaseEvent;

public class UpdateListCountEvent extends BaseEvent {

	public final Model model; 
	
	public UpdateListCountEvent(Model model){
		this.model = model;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
