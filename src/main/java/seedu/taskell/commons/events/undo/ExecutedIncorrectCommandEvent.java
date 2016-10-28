package seedu.taskell.commons.events.undo;

import seedu.taskell.commons.events.BaseEvent;

/** Indicates a Command has been entered incorrectly
 *  NOTE: this is different from IncorrectCommandAttemptedEvent (which is meant for UI)
 */

public class ExecutedIncorrectCommandEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
