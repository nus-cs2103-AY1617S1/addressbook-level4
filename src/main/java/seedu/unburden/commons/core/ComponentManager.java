package seedu.unburden.commons.core;

import java.util.function.Predicate;

import seedu.unburden.commons.events.BaseEvent;
import seedu.unburden.model.task.Task;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public abstract class ComponentManager {
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

	public void updateFilteredListToShow(Predicate<? super Task> predicate) {
		// TODO Auto-generated method stub
		
	}
}
