package seedu.todo.testutil;

import com.google.common.eventbus.Subscribe;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.BaseEvent;

import java.util.ArrayList;
import java.util.List;

//@@author A0135817B-reused
/**
 * A class that collects events raised by other classes.
 */
public class EventsCollector{
    private List<BaseEvent> events = new ArrayList<>();

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
     * Returns the event at the specified index
     */
    public BaseEvent get(int index){
        return events.get(index);
    }
    
    public BaseEvent last() {
        return events.get(events.size() - 1);
    }
    
    public int size(){
        return events.size();
    }
}
