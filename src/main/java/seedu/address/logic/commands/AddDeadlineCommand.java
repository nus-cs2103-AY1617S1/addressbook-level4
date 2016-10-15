package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.TaskDateTime;

public class AddDeadlineCommand extends AddCommand {

    public AddDeadlineCommand(String name, String endDate) 
            throws IllegalValueException {
        super(
            new DeadlineTask(
                new Name(name), 
                new TaskDateTime(endDate))
            );
    }
}
