package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.EventTask;
import seedu.taskscheduler.model.task.Location;
import seedu.taskscheduler.model.task.Name;
import seedu.taskscheduler.model.task.TaskDateTime;

public class AddEventCommand extends AddCommand {

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddEventCommand(String name, String startDate, String endDate, String address) 
            throws IllegalValueException {
        
        super(  
            new EventTask(
                new Name(name),
                new TaskDateTime(startDate),
                new TaskDateTime(endDate),
                new Location(address)
                )
            );
        
    }

}
