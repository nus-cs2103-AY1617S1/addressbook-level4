package seedu.malitio.commons.events.ui;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.task.ReadOnlySchedule;
import seedu.malitio.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private ReadOnlyTask newSelection;
    private ReadOnlySchedule newScheduleSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection){
        this.newSelection = newSelection;
    }
    public TaskPanelSelectionChangedEvent(ReadOnlySchedule newSelection){
        this.newScheduleSelection = newSelection;
    }
    

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
