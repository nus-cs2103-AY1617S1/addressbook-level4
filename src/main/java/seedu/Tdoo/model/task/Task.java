package seedu.Tdoo.model.task;

import seedu.Tdoo.model.task.attributes.Done;
import seedu.Tdoo.model.task.attributes.Name;

/**
 * Represents a task in the TaskList.
 * Guarantees: details are present and not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask {

    protected Name name;
    protected String isDone;
    
    public Name getName() {
    	return this.name;
    }
    
    //@@author A0139920A
    public String getDone() {
    	return this.isDone;
    }
    
    public void setDone(String isDone) {
    	this.isDone = isDone;
    }
}