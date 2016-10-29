package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Model;

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
