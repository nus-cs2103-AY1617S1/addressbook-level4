package seedu.cmdo.commons.events.model;

import seedu.cmdo.commons.events.BaseEvent;
import seedu.cmdo.model.ModelManager;
import seedu.cmdo.model.ReadOnlyToDoList;

/** Indicates the CMDo in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;
    public final Class calling;

    public ToDoListChangedEvent(ReadOnlyToDoList data) {
    	this.data = data;
    	// By default the model called it.
    	this.calling = ModelManager.class;
    }
    
    public ToDoListChangedEvent(ReadOnlyToDoList data, Class calling){
        this.data = data;
        this.calling = calling;
    }

    @Override
    public String toString() {
        return "details " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
