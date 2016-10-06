package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected EventsCenter eventsCenter;

    /**
     * Executes the command
     *
     * @returns result of the command
     */
    public abstract CommandResult execute();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies
     * Asserts {@param model} to be non-null
     */
    public void setModel(Model model) {
        assert model != null;

        this.model = model;
    }

    /**
     * Provides an events center for commands to post events
     * Asserts {@param eventsCenter} to be non-null
     */
    public void setEventsCenter(EventsCenter eventsCenter) {
        assert eventsCenter != null;

        this.eventsCenter = eventsCenter;
    }
}
