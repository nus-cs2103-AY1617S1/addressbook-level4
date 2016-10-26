package seedu.whatnow.commons.events.model;
//A0126240W
import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.task.Task;

/** Indicates that a task has been updated in WhatNow*/
public class UpdateTaskEvent extends BaseEvent {

    public final Task task;

    public UpdateTaskEvent(Task task){
        this.task = task;
    }

    @Override
    public String toString() {
        return task.getAsText();
    }
}
