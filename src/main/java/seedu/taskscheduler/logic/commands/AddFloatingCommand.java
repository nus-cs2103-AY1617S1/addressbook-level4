package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.FloatingTask;
import seedu.taskscheduler.model.task.Name;

//@@author A0148145E

/**
* Adds a floating task to the Task Scheduler.
*/

public class AddFloatingCommand extends AddCommand {

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddFloatingCommand(String name) throws IllegalValueException {
        super(
            new FloatingTask(
                new Name(name))
            );
    }
}
