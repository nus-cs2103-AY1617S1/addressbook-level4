package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.DeadlineTask;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.TaskDateTime;

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
