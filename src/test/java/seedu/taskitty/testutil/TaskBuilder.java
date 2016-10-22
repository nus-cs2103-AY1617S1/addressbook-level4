package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.task.*;

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
        this.task.setPeriod(new TaskPeriod());
        return this;
    }
    
    public TaskBuilder withDeadline(String endDate, String endTime) throws IllegalValueException {
        this.task.setPeriod(new TaskPeriod(new TaskDate(endDate), new TaskTime(endTime)));
        return this;
    }
    
    public TaskBuilder withEvent(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
        this.task.setPeriod(new TaskPeriod(new TaskDate(startDate), new TaskTime(startTime),
                        new TaskDate(endDate), new TaskTime(endTime)));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
