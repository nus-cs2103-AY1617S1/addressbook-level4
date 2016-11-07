package jym.manager.commons.events.ui;

import jym.manager.commons.events.BaseEvent;
import jym.manager.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    private Command command;

    public IncorrectCommandAttemptedEvent(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
