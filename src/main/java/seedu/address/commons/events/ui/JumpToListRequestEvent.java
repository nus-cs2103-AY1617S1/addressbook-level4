package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

	public static int UNDATED_LIST = 1;
	public static int DATED_LIST = 2;
	
    public final int targetIndex;
    public final int listType;
    

    public JumpToListRequestEvent(int targetIndex, int listType) {
        this.targetIndex = targetIndex;
        this.listType = listType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
