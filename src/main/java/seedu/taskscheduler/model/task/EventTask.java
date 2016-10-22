package seedu.taskscheduler.model.task;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;

public class EventTask extends Task{

    public EventTask(Name name, TaskDateTime startDateTime, TaskDateTime endDateTime, Location address) throws IllegalValueException {
        super(name, startDateTime, endDateTime, address, new UniqueTagList(new Tag("Event")));
    }
    
    public EventTask(ReadOnlyTask source) {
        super(source);
    }

//    @Override
//    public String getParamOne() {
//        // TODO Auto-generated method stub
//        return getLocation().toString();
//    }
//
//    @Override
//    public String getParamTwo() {
//        // TODO Auto-generated method stub
//        return "Start Date: " + getStartDate().getDisplayString();
//    }
//
//    @Override
//    public String getParamThree() {
//        // TODO Auto-generated method stub
//        return "End Date: " + getEndDate().getDisplayString();
//    }
//

    @Override
    public Task copy() {
        return new EventTask(this);
    }
}
