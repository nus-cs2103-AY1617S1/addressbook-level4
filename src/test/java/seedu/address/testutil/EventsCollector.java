package seedu.address.testutil;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A class that collects events raised by other classes.
 */
public class EventsCollector {
    List<BaseEvent> events = new ArrayList<BaseEvent>();

    public EventsCollector(){
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Collects any event raised by any class
     */
    @Subscribe
    public void collectEvent(BaseEvent event){
        events.add(event);
    }

    /**
     * Removes collected events from the collected list
     */
    public void reset(){
        events.clear();
    }

    /**
     * Check if event type has been collected
     */
    public boolean hasCollectedEvent(Class<? extends BaseEvent> eventClass) {
        return events.stream().filter(eventClass::isInstance).count() > 0;
    }

    /**
     * Get the first collected event of type
     */
    public <T extends BaseEvent> Optional<T> getCollectedEvent(Class<T> eventClass) {
        List<BaseEvent> matched = events.stream().filter(eventClass::isInstance).collect(Collectors.toList());
        return matched.size() > 0 ? Optional.of((T)matched.get(0)) : Optional.empty();
    }
}
