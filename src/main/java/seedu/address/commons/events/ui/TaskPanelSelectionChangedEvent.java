package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;
    private final int index;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection, int index){
        this.newSelection = newSelection;
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
    
    public int getIndex(){
    	return index;
    }
}
