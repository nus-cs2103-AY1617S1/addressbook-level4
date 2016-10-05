package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.taskcommands.TaskCommand;

public class IncorrectTaskCommandAttemptedEvent extends BaseEvent {

    public IncorrectTaskCommandAttemptedEvent(TaskCommand command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}