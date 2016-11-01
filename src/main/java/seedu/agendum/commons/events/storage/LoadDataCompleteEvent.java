package seedu.agendum.commons.events.storage;

import seedu.agendum.commons.events.BaseEvent;
import seedu.agendum.model.ReadOnlyToDoList;

//@@author A0148095X
/** Indicates the ToDoList load request has completed successfully **/
public class LoadDataCompleteEvent extends BaseEvent {

    public final ReadOnlyToDoList data;
    
    private String message;

    public LoadDataCompleteEvent(ReadOnlyToDoList data){
        this.data = data;
        this.message = "Todo list data load completed. Task list size: " + data.getTaskList().size();
    }

    @Override
    public String toString() {
        return message;
    }
}
