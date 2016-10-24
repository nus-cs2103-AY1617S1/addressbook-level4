package seedu.whatnow.commons.events.model;

import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.ReadOnlyWhatNow;

/** Indicates the WhatNow config in the model has changed*/
public class WhatNowChangedEvent extends BaseEvent {

    public final ReadOnlyWhatNow data;

    public WhatNowChangedEvent(ReadOnlyWhatNow data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
