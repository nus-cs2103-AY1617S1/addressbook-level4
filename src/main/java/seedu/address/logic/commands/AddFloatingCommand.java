package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;

public class AddFloatingCommand extends AddCommand {

    public AddFloatingCommand(String name) throws IllegalValueException {
        super(
            new FloatingTask(
                new Name(name))
            );
    }
}
