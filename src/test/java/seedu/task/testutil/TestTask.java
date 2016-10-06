package seedu.task.testutil;

import seedu.task.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Boolean isTaskCompleted;

    public TestTask() {
        isTaskCompleted = false;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Boolean getTaskStatus() {
        return isTaskCompleted;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("desc/" + this.getDescription().value + " ");
        return sb.toString();
    }

}
