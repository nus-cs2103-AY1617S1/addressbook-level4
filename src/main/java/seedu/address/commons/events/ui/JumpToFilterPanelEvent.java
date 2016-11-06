package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.Types;

/**
 * Indicates a request to jump to the filter panel
 */
public class JumpToFilterPanelEvent extends BaseEvent {
    
    private final Types qualification;
    
    public JumpToFilterPanelEvent(Types deadline) {
        this.qualification = deadline;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public Types getQualification() {
        return qualification;
    }
}
