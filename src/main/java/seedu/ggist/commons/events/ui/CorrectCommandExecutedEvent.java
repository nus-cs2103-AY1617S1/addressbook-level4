package seedu.ggist.commons.events.ui;

import seedu.ggist.commons.events.BaseEvent;
import seedu.ggist.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class CorrectCommandExecutedEvent extends BaseEvent {

    public CorrectCommandExecutedEvent(Command command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
