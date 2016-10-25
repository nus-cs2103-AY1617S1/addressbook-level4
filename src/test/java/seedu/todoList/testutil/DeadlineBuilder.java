package seedu.todoList.testutil;


import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
/**
 *
 */
public class DeadlineBuilder {

    private TestDeadline task;

    public DeadlineBuilder() {
        this.task = new TestDeadline();
    }

    public DeadlineBuilder withDeadline(Deadline dl) throws IllegalValueException {
        this.task.setDeadline(new Deadline(dl));
        return this;
    }
    
    public DeadlineBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public DeadlineBuilder withDate(String date) throws IllegalValueException {
        this.task.setDate(new StartDate(date));
        return this;
    }

    public DeadlineBuilder withEndTime(String et) throws IllegalValueException {
        this.task.setEndTime(new EndTime(et));
        return this;
    }
    public DeadlineBuilder withDone(String et) throws IllegalValueException {
        this.task.setDone(new Done(et));
        return this;
    }

    public TestDeadline build() {
        return this.task;
    }

}
