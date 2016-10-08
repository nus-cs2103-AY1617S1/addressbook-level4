package seedu.unburden.commons.events.model;

import seedu.unburden.commons.events.BaseEvent;
import seedu.unburden.model.ReadOnlyListOfTask;

/** Indicates the ListOfTask in the model has changed*/
public class ListOfTaskChangedEvent extends BaseEvent {

    public final ReadOnlyListOfTask data;

    public ListOfTaskChangedEvent(ReadOnlyListOfTask data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
