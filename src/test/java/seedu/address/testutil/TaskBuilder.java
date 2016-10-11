package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDetail(String detail) throws IllegalValueException {
        this.task.setDetail(new Detail(detail));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDueByDate(String dbd) throws IllegalValueException {
    	assert dbd.matches("[1-12]/[1-31]/[2000-2999]");
        this.task.setDueByDate(new DueByDate(LocalDate.parse(dbd, DateTimeFormatter.ISO_LOCAL_DATE)));
        return this;
    }

    public TaskBuilder withDueByTime(String dbt) throws IllegalValueException {
    	assert dbt.matches("[0-1]?[0-9]:[0-5][0-9]");
        this.task.setDueByTime(new DueByTime(LocalTime.parse(dbt, DateTimeFormatter.ISO_LOCAL_TIME)));
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
