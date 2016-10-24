package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.DeadlineTask;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.TaskDateTime;

//@@author A0148145E

/**
 * Adds a deadline task to the Task Scheduler.
 */
public class AddDeadlineCommand extends AddCommand {

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddDeadlineCommand(String name, String endDate) 
            throws IllegalValueException {
        super(
            new DeadlineTask(
                new Name(name), 
                new TaskDateTime(endDate))
            );
    }
}
