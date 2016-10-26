package seedu.todo.testutil;

import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Detail detail;
    private TaskDate fromDate;
    private TaskDate tillDate;
    private UniqueTagList tags;
    private Priority priority;
    private Completion completion;
    private Recurrence recurrence;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public void setOnDate(TaskDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setByDate(TaskDate tillDate) {
        this.tillDate = tillDate;
    }
    
    public void setCompletion(Completion completion) {
        this.completion = completion;
    }
    
    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Detail getDetail() {
        return this.detail;
    }

    @Override
    public TaskDate getOnDate() {
        return this.fromDate;
    }

    @Override
    public TaskDate getByDate() {
        return this.tillDate;
    }

    @Override
    public Completion getCompletion() {
        return this.completion;
    }
    
    @Override
    public Recurrence getRecurrence() {
        return this.recurrence;
    }
    
    @Override
    public boolean isRecurring() {
        return this.recurrence.isRecurring();
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("on " + this.getOnDate().toString() + " ");
        sb.append("by " + this.getByDate().toString() + " ");
        sb.append("; " + this.getDetail().value + " ");
        return sb.toString();
    }
}
