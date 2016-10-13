package seedu.savvytasker.testutil;

import seedu.savvytasker.commons.exceptions.IllegalValueException;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTaskName(String taskName) throws IllegalValueException {
        this.task.setTaskName(taskName);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
