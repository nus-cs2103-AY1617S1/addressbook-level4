package seedu.jimi.commons.events.ui;

import seedu.jimi.commons.events.BaseEvent;

/**
 * Indicates user request to show a section of the taskList panel.
 * @@author A0138915X
 *
 */
public class ShowTaskPanelSectionEvent extends BaseEvent{

    public final String sectionToDisplay;
    
    public ShowTaskPanelSectionEvent(String sectionToDisplay) {
        this.sectionToDisplay = sectionToDisplay;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
