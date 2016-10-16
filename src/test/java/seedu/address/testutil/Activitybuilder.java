package seedu.address.testutil;

import com.fasterxml.jackson.core.sym.Name;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;
import seedu.menion.model.tag.Tag;

/**
 *
 */
public class Activitybuilder {

    private TestActivity activity;
    
    public Activitybuilder() {
        
    }
    public Activitybuilder(ReadOnlyActivity newActivity ) {
        this.activity = new TestActivity(newActivity);
    }

    public Activitybuilder withFloatingTask(String type, ActivityName name, Note note) throws IllegalValueException {
        
        this.activity = new TestActivity(type, name, note);
        return this;
    }
    
    public Activitybuilder withTask(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime) throws IllegalValueException {
       
        this.activity = new TestActivity(type, name, note, startDate, startTime);
        return this;
    }

    public Activitybuilder withEvent(String type, ActivityName name, Note note, ActivityDate startDate, 
            ActivityTime startTime, ActivityDate endDate, ActivityTime endTime) throws IllegalValueException {
        
        this.activity = new TestActivity(type, name, note, startDate, startTime, endDate, endTime);
        return this;
    }

    public TestActivity build() {
        return this.activity;
    }

}
