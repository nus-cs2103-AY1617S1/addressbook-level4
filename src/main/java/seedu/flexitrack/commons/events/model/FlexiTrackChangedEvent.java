package seedu.flexitrack.commons.events.model;

import seedu.flexitrack.commons.events.BaseEvent;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;

/** Indicates the FlexiTrack in the model has changed */
public class FlexiTrackChangedEvent extends BaseEvent {

    public final ReadOnlyFlexiTrack data;

    public FlexiTrackChangedEvent(ReadOnlyFlexiTrack data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
