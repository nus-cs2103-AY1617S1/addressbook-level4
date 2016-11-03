//@@author A0141052Y
package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

/**
 * Indicates that the CommandBox functionality should switch
 * @author Syed Abdullah
 *
 */
public class SwitchCommandBoxFunctionEvent extends BaseEvent {
    
    public SwitchCommandBoxFunctionEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
