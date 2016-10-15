package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Location;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskDateTime;

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
