package seedu.whatnow.commons.events.model;
//@@author A0139772U
import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.task.Task;

/** Indicates that a task has been added to WhatNow*/
public class AddTaskEvent extends BaseEvent {

    public final Task task;

    public AddTaskEvent(Task task){
        this.task = task;
    }

    @Override
    public String toString() {
        return task.getAsText();
    }
}
