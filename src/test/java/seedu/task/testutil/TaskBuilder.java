package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withImportance(boolean isImportant) throws IllegalValueException{
    	this.task.setIsImportant(isImportant);
    	return this;
    }
    //@@author A0144939R
    public TaskBuilder withOpenTime(String openTime) throws IllegalValueException {
        this.task.setOpenTime(new DateTime(openTime));
        return this;
    }
    
    public TaskBuilder withCloseTime(String closeTime) throws IllegalValueException {
        this.task.setCloseTime(new DateTime(closeTime));
        return this;
    }
    //@@author
    public TestTask build() {
        return this.task;
    }

}
