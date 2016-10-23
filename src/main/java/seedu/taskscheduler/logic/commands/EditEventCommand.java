package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.EventTask;
import seedu.taskscheduler.model.task.Location;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.TaskDateTime;

public class EditEventCommand extends EditCommand {
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditEventCommand(int targetIndex, String name, String startDate, String endDate, String address)
            throws IllegalValueException {
        super(
            targetIndex,
            new EventTask(
                new Name(name),
                new TaskDateTime(startDate),
                new TaskDateTime(endDate),
                new Location(address))
        );
    }
}
