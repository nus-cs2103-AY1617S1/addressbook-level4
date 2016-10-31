package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author A0135767U
public class ViewCategoryChangedEvent extends BaseEvent{
	
	private String category;

    public ViewCategoryChangedEvent(String category){
        this.category = category;
    }
	
	@Override
	public String toString() {
		return "request to show " + category + " tasks";
	}
	
	public String getView() {
		return category;
	}

}
