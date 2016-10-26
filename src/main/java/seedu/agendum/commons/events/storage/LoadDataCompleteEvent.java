package seedu.agendum.commons.events.storage;

import seedu.agendum.commons.events.BaseEvent;
import seedu.agendum.model.ReadOnlyToDoList;

//@@author A0148095X
/** Indicates the ToDoList load request has completed successfully **/
public class LoadDataCompleteEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public LoadDataCompleteEvent(ReadOnlyToDoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "Todo list data load completed. Task list size: " + data.getTaskList().size();
    }
}
