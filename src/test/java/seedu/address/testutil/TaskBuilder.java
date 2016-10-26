package seedu.address.testutil;

import java.text.ParseException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Interval;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Status;
import seedu.task.model.task.TimeInterval;
import seedu.task.model.task.Title; 

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }
    
    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withStartDate(String startDate) throws IllegalValueException{
        try {
			this.task.setStartDate(new StartDate(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this;
    }
    
    public TaskBuilder withDueDate(String dueDate) throws IllegalValueException {
        try {
			this.task.setDueDate(new DueDate(dueDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this;
    }
    
    public TaskBuilder withInterval(String interval) throws IllegalValueException {
        this.task.setInterval(new Interval(interval));
        return this;
    }
    
    public TaskBuilder withTimeInterval(String timeInterval) throws IllegalValueException {
        this.task.setTimeInterval(new TimeInterval(timeInterval));
        return this;
    }
    
    public TaskBuilder withStatus(String status) throws IllegalValueException {
        this.task.setStatus(new Status(status));
        return this;
    }
    
    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
