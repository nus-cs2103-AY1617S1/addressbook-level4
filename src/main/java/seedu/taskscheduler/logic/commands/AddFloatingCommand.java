package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.FloatingTask;
import seedu.taskscheduler.model.task.Name;

public class AddFloatingCommand extends AddCommand {

    public AddFloatingCommand(String name) throws IllegalValueException {
        super(
            new FloatingTask(
                new Name(name))
            );
    }
}
