package seedu.ggist.commons.events.ui;

import seedu.ggist.commons.events.BaseEvent;
//@@author A0138411N
public class AddTaskEvent extends BaseEvent{

    public int index;
    public AddTaskEvent(int index) {
        this.index = index;
    }
    @Override
    public String toString() {
        return  this.getClass().getSimpleName();
    }
}
