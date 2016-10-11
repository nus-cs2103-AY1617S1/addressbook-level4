package seedu.address.testutil;

import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

/**
 * A mutable person object. For testing only.
 */
public class TestTask extends Task {

    public TestTask(Name name) {
        super(name);
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.name.fullName + " ");
        return sb.toString();
    }
}
