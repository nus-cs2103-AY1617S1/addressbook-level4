package seedu.malitio.commons.events.model;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.ReadOnlyMalitio;

/** Indicates the Malitio in the model has changed*/
public class MalitioChangedEvent extends BaseEvent {

    public final ReadOnlyMalitio data;

    public MalitioChangedEvent(ReadOnlyMalitio data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks "
                + data.getFloatingTaskList().size() + data.getDeadlineList().size() + data.getEventList().size() 
                + ", number of tags " + data.getTagList().size();
    }
}
