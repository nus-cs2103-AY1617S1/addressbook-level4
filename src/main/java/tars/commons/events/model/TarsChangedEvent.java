package tars.commons.events.model;

import tars.commons.events.BaseEvent;
import tars.model.ReadOnlyTars;

/** Indicates the Tars in the model has changed */
public class TarsChangedEvent extends BaseEvent {

    private static String TARS_SUMMARY =
            "number of tasks %1$s, number of reserved tasks %2$s, number of tags %3$s";

    public final ReadOnlyTars data;

    public TarsChangedEvent(ReadOnlyTars data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(TARS_SUMMARY, data.getTaskList().size(),
                data.getRsvTaskList().size(), data.getTagList().size());
    }
}
