package seedu.task.testutil;

import java.util.Optional;

import seedu.task.model.item.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Optional<Description> description;
    private Optional<Deadline> deadline;
    private Boolean isTaskCompleted;

    public TestTask() {
    	//default fields
        this.isTaskCompleted = false;
        this.deadline = Optional.empty();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = Optional.of(description);
    }
    
    public void setDeadline(Deadline deadline) {
        this.deadline = Optional.of(deadline);
    
    }
    
    public void setStatus(Boolean status) {
        this.isTaskCompleted = status;
    }

    @Override
    public Name getTask() {
        return name;
    }

    public Description getDescriptionRaw() {
        return description.get();
    }

    public Deadline getDeadlineRaw() {
        return this.deadline.get();
    }

    @Override
    public Boolean getTaskStatus() {
        return isTaskCompleted;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public String getFullAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTask().fullName + " ");
        sb.append("/desc " + this.getDescriptionValue() + " ");
        sb.append("/by " + this.getDeadlineValue() + " ");
        return sb.toString();
    }
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTask().fullName + " ");
        sb.append("/desc " + this.getDescriptionValue() + " ");
        return sb.toString();
    }
    
    public String getEditFloatTaskCommand(int index) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit -t " + index + " ");
        sb.append("/name " + this.getTask().fullName + " ");
        sb.append("/desc " + this.getDescriptionValue() + " ");
        return sb.toString();
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return this.deadline;
    }
    
    @Override
    public Optional<Description> getDescription() {
        return this.description;
    }
    
}
