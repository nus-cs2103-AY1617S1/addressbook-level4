package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    /**
     * Executes the command, in the context of {@param model}and {@param eventsCenter}
     * @returns result of the command
     */
    public abstract CommandResult execute(Model model, EventsCenter eventsCenter);
}
