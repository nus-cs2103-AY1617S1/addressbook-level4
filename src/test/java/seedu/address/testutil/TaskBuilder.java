package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *@@author A0138993L
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withEnd(String address) throws IllegalValueException {
        this.person.setEnd(new End(address));
        return this;
    }

    public TaskBuilder withDate(String phone) throws IllegalValueException {
        this.person.setDate(new Date(phone));
        return this;
    }

    public TaskBuilder withStart(String email) throws IllegalValueException {
        this.person.setStart(new Start(email));
        return this;
    }
    
    public TaskBuilder withTaskCat(int taskCat) throws IllegalValueException {
    	this.person.setTaskCategory(taskCat);
    	return this;
    }
    
    public TaskBuilder withOverdue(int overdue) throws IllegalValueException {
    	this.person.setOverdue(overdue);
    	return this;
    }
    
    public TaskBuilder withIsCompleted(boolean isCompleted) throws IllegalValueException {
    	this.person.setIsCompleted(isCompleted);
    	return this;
    }

    public TestTask build() {
        return this.person;
    }

}
