package seedu.todoList.testutil;


import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
/**
 *
 */
//@@author A0132157M
public class DeadlineBuilder {

    private TestDeadline task;

    public DeadlineBuilder() {
        this.task = new TestDeadline();
    }

    
    public DeadlineBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public DeadlineBuilder withStartDate(String date) throws IllegalValueException {
        this.task.setDate(date);
        return this;
    }

    public DeadlineBuilder withEndTime(String et) throws IllegalValueException {
        this.task.setEndTime(et);
        return this;
    }
    public DeadlineBuilder withDone(String et) throws IllegalValueException {
        this.task.setDone(et);
        return this;
    }

    public TestDeadline build() {
        return this.task;
    }

}
