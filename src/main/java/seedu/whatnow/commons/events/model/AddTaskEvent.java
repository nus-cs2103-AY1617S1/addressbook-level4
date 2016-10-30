//@@author A0139772U-reused
package seedu.whatnow.commons.events.model;

import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.task.Task;

/** Indicates that a task has been added to WhatNow*/
public class AddTaskEvent extends BaseEvent {

    public final Task task;
    public final boolean isUndo;

    public AddTaskEvent(Task task, boolean isUndo){
        this.task = task;
        this.isUndo = isUndo;
    }

    @Override
    public String toString() {
        return task.getAsText();
    }
}
