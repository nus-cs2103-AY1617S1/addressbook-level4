package seedu.tasklist.commons.events.storage;

import seedu.tasklist.commons.events.BaseEvent;

public class ChangePathEvent extends BaseEvent{

	private String filepath;
	
	
	public ChangePathEvent(String filepath){
		this.filepath = filepath;
	}
	
	@Override
	public String toString() {
		return filepath;
	}

}
