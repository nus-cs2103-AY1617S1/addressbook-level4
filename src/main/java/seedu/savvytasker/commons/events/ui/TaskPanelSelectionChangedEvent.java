package seedu.savvytasker.commons.events.ui;

import seedu.savvytasker.commons.events.BaseEvent;
import seedu.savvytasker.model.task.ReadOnlyTask;

//@@author A0139915W

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
//@@author A0139915W