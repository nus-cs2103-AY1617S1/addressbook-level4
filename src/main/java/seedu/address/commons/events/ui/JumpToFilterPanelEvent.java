package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.Types;

//@@author A0146123R
/**
 * Indicates a request to jump to the filter panel.
 */
public class JumpToFilterPanelEvent extends BaseEvent {
    
    private final Types attribute;
    
    public JumpToFilterPanelEvent(Types deadline) {
        this.attribute = deadline;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public Types getAttribute() {
        return attribute;
    }
}
