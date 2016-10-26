package seedu.todo.testutil;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.*;
import seedu.todo.model.task.Recurrence.Frequency;

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
    
    public TaskBuilder withCompletion(boolean completed) {
        this.task.setCompletion(new Completion(completed));
        return this;
    }
    
    public TaskBuilder withRecurrence(Frequency freq) throws IllegalValueException{
        this.task.setRecurrence(new Recurrence(freq));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDetail(String detail) throws IllegalValueException {
        this.task.setDetail(new Detail(detail));
        return this;
    }

    public TaskBuilder withOnDate(String dateString) throws IllegalValueException {
        this.task.setOnDate(new TaskDate(dateString, TaskDate.TASK_DATE_ON));
        return this;
    }

    public TaskBuilder withByDate(String dateString) throws IllegalValueException {
        this.task.setByDate(new TaskDate(dateString, TaskDate.TASK_DATE_BY));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }
    
    
    public TestTask build() {
        return this.task;
    }

}
