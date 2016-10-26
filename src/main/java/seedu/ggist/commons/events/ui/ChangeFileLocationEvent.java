package seedu.ggist.commons.events.ui;

import seedu.ggist.commons.events.BaseEvent;
//@@author A0138411N
public class ChangeFileLocationEvent extends BaseEvent{

    public String path;
    public ChangeFileLocationEvent(String path) {
        this.path = path;
    }
    @Override
    public String toString() {
        return  this.getClass().getSimpleName();
    }
}
