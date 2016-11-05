package seedu.malitio.commons.events.ui;

import seedu.malitio.commons.events.BaseEvent;

public class JumpToListRequestEvent extends BaseEvent{

    public final int targetIndex;
    public final String typeOfTask;

    public JumpToListRequestEvent(int targetIndex, String typeOfTask) {
        this.targetIndex = targetIndex;
        this.typeOfTask = typeOfTask;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
