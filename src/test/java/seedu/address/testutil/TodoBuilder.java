package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *@@author A0138993L
 */
public class TodoBuilder {

    private TestTodo person;

    public TodoBuilder() {
        this.person = new TestTodo();
    }

    public TodoBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TodoBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TodoBuilder withEnd(String address) throws IllegalValueException {
        this.person.setEnd(new End(address));
        return this;
    }

    public TodoBuilder withDate(String phone) throws IllegalValueException {
        this.person.setDate(new Date(phone));
        return this;
    }

    public TodoBuilder withStart(String email) throws IllegalValueException {
        this.person.setStart(new Start(email));
        return this;
    }
    
    public TodoBuilder withTaskCat(int taskCat) throws IllegalValueException {
    	this.person.setTaskCategory(taskCat);
    	return this;
    }
    
    public TodoBuilder withOverdue(int overdue) throws IllegalValueException {
    	this.person.setOverdue(overdue);
    	return this;
    }
    
    public TodoBuilder withIsCompleted(boolean isCompleted) throws IllegalValueException {
    	this.person.setIsCompleted(isCompleted);
    	return this;
    }

    public TestTodo build() {
        return this.person;
    }

}
