package seedu.taskscheduler.model.task;

import seedu.taskscheduler.model.tag.UniqueTagList;

public class FloatingTask extends Task {

    public FloatingTask(Name name) {
        super(name, new TaskDateTime(), new TaskDateTime(), new Location(), new UniqueTagList());
    }

    public FloatingTask(ReadOnlyTask source) {
        super(source);
    }
    
//    @Override
//    public String getParamOne() {
//        return "";
//    }
//
//    @Override
//    public String getParamTwo() {
//        return "";
//    }
//
//    @Override
//    public String getParamThree() {
//        return "";
//    }
//
    
    @Override
    public Task copy() {
        return new FloatingTask(this);
    }
}
