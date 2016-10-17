package teamfour.tasc.commons.events.storage;

import teamfour.tasc.commons.events.BaseEvent;

/** Indicates the Task List in the model has changed*/
public class RequestTaskListSwitchEvent extends BaseEvent {

    private final String filename;
    
    public String getFilename() {
        return filename;
    }

    public RequestTaskListSwitchEvent(String filename){
        this.filename = filename;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
