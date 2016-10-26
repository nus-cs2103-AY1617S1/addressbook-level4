package seedu.address.commons.core;

import seedu.address.commons.events.BaseEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public abstract class ComponentManager {
	@JsonIgnore
    protected EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public ComponentManager(){
        this(EventsCenter.getInstance());
    }

    public ComponentManager(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event){
        eventsCenter.post(event);
    }
}
