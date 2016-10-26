package seedu.malitio.commons.events.ui;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.task.ReadOnlyDeadline;

public class DeadlinePanelSelectionChangedEvent extends BaseEvent{
    private ReadOnlyDeadline newDeadlineSelection;
    
    public DeadlinePanelSelectionChangedEvent(ReadOnlyDeadline newSelection) {
        this.newDeadlineSelection = newSelection;
    }  
    public ReadOnlyDeadline getNewDeadlineSelection() {
        return newDeadlineSelection;
    }    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
 
}
