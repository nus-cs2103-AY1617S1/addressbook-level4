package w15c2.tusk.testutil;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.model.task.Description;
//@@author A0143107U
/**
 * Builds a task for testing
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder setCompleted(boolean isCompleted) throws IllegalValueException {
        this.task.setCompleted(isCompleted);
        return this;
    }

    public TaskBuilder setPinned(boolean isPinned) throws IllegalValueException {
        this.task.setPinned(isPinned);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
