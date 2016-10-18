package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.tag.Tag;

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
    
    public TaskBuilder withPriority(String priorityValue) throws IllegalValueException {
        Priority priority;
        switch (priorityValue) {
            case ("low"): 
                priority = Priority.LOW; 
                break;
            case ("medium"): 
                priority = Priority.MEDIUM; 
                break;
            case ("high"): 
                priority = Priority.HIGH; 
                break;
            default:
                priority = Priority.MEDIUM;
        }
        
        this.task.setPriority(priority);
        return this;
    }

    /*
    public FloatingTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            floatingTask.getTags().add(new Tag(tag));
        }
        return this;
    }
    */
    public TestTask build() {
        return this.task;
    }

}
