package teamfour.tasc.commons.events.storage;

import teamfour.tasc.commons.events.BaseEvent;

public class FileRelocateEvent extends BaseEvent {

    private String destination;
    
    public FileRelocateEvent(String destination) {
        this.destination = destination;
    }
    
    public String getDestination() {
        return destination;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
