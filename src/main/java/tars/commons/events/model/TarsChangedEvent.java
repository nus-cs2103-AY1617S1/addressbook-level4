package tars.commons.events.model;

import tars.commons.events.BaseEvent;
import tars.model.ReadOnlyTars;

/** Indicates the Tars in the model has changed */
public class TarsChangedEvent extends BaseEvent {

    public final ReadOnlyTars data;

    public TarsChangedEvent(ReadOnlyTars data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of reserved tasks "
                + data.getRsvTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
