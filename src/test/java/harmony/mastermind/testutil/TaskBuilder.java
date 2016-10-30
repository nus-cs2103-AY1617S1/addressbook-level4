package harmony.mastermind.testutil;

import java.util.Set;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList.DuplicateTagException;
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
    
    //@@author A0124797R
    public TaskBuilder withRecur(String recur) {
        this.task.setRecur(recur);
        return this;
    }
    
    public TaskBuilder withTags(String... tags) throws IllegalValueException{
        for (String tagName : tags) {
            task.getTags().add(new Tag(tagName));
        }
        return this;
    }
    
    public TaskBuilder withTags(Set<String> tags) throws IllegalValueException{
        for (String tagName : tags) {
            task.getTags().add(new Tag(tagName));
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
