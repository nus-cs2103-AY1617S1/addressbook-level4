package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *@@author A0138993L
 */
public class DeadlineBuilder {

    private TestDeadline person;

    public DeadlineBuilder() {
        this.person = new TestDeadline();
    }

    public DeadlineBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public DeadlineBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public DeadlineBuilder withEnd(String address) throws IllegalValueException {
        this.person.setEnd(new End(address));
        return this;
    }

    public DeadlineBuilder withDate(String phone) throws IllegalValueException {
        this.person.setDate(new Date(phone));
        return this;
    }

    public DeadlineBuilder withStart(String email) throws IllegalValueException {
        this.person.setStart(new Start(email));
        return this;
    }
    
    public DeadlineBuilder withTaskCat(int taskCat) throws IllegalValueException {
    	this.person.setTaskCategory(taskCat);
    	return this;
    }
    
    public DeadlineBuilder withOverdue(int overdue) throws IllegalValueException {
    	this.person.setOverdue(overdue);
    	return this;
    }
    
    public DeadlineBuilder withIsCompleted(boolean isCompleted) throws IllegalValueException {
    	this.person.setIsCompleted(isCompleted);
    	return this;
    }

    public TestDeadline build() {
        return this.person;
    }

}
