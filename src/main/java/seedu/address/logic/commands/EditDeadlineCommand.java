package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskDateTime;

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
