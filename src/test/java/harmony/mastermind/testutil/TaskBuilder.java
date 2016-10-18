package harmony.mastermind.testutil;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    //@@author A0124797R
    public TaskBuilder withName(String name) {
        this.task.setName(name);
        return this;
    }
    
    //@@author A0124797R
    public TaskBuilder withStartDate(String date) {
        this.task.setStartDate(date);
        return this;
    }
    
    //@@author A0124797R
    public TaskBuilder withEndDate(String date) {
        this.task.setEndDate(date);
        return this;
    }


    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }
    
    public TaskBuilder mark() {
        return this.mark();
        
    }

    public TestTask build() {
        return this.task;
    }

}
