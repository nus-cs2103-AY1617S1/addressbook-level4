package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;

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