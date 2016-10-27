package seedu.cmdo.commons.events.model;

import seedu.cmdo.commons.events.BaseEvent;
import seedu.cmdo.model.ReadOnlyToDoList;

/** Indicates the AddressBook in the model has changed*/
public class UndoActionEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public UndoActionEvent(ReadOnlyToDoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
