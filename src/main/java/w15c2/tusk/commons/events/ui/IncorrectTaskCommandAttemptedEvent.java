package w15c2.tusk.commons.events.ui;

import w15c2.tusk.commons.events.BaseEvent;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

public class IncorrectTaskCommandAttemptedEvent extends BaseEvent {

    public IncorrectTaskCommandAttemptedEvent(TaskCommand command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}