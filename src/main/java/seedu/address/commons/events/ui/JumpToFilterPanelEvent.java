package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the filter panel
 */
public class JumpToFilterPanelEvent extends BaseEvent {
    
    private final String qualification;
    
    public JumpToFilterPanelEvent(String qualification) {
        this.qualification = qualification;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public String getQualification() {
        return qualification;
    }
}
