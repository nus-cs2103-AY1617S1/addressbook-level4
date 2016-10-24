package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.FloatingTask;
import seedu.taskscheduler.model.task.Name;

//@@author A0148145E

/**
 * Edits a floating task in the task scheduler.
 */
public class EditFloatingCommand extends EditCommand {
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditFloatingCommand(int targetIndex, String name)
            throws IllegalValueException {
        super(
            targetIndex,
            new FloatingTask(
                new Name(name))
        );
    }
}