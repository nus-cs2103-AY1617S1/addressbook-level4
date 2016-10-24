package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.DeadlineTask;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.TaskDateTime;

//@@author A0148145E

/**
 * Edits a deadline task in the task scheduler.
 */
public class EditDeadlineCommand extends EditCommand {

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditDeadlineCommand(int targetIndex, String name, String endDate)
            throws IllegalValueException {
        super(
            targetIndex,
            new DeadlineTask(
                new Name(name),
                new TaskDateTime(endDate))
        );
    }
}
