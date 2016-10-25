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
        int totalSize = data.getFloatingTaskList().size()
                + data.getDeadlineList().size() 
                + data.getEventList().size();
        
        return "number of tasks "
                + totalSize 
                + ", number of tags " + data.getTagList().size();
    }
}
