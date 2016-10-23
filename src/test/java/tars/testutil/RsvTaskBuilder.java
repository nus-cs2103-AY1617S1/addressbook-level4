package tars.testutil;

import tars.commons.exceptions.IllegalValueException;
import tars.model.task.DateTime;
import tars.model.task.Name;

public class RsvTaskBuilder {
    
    private TestRsvTask rsvTask;
    
    public RsvTaskBuilder() {
        this.rsvTask = new TestRsvTask();
    }

    public RsvTaskBuilder withName(String name) throws IllegalValueException {
        this.rsvTask.setName(new Name(name));
        return this;
    }

    public RsvTaskBuilder withDateTime(String dateTime1, String dateTime2) throws IllegalValueException {
        this.rsvTask.setDateTimeList(new DateTime(dateTime1, dateTime2));
        return this;
    }


    public TestRsvTask build() {
        return this.rsvTask;
    }

}
