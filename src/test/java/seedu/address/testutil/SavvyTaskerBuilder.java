package seedu.address.testutil;

import seedu.address.model.SavvyTasker;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskList.DuplicateTaskException;

/**
 * A utility class to help with building SavvyTasker objects.
 * Example usage: <br>
 *     {@code SavvyTasker st = new SavvyTaskerBuilder().withTask("Hello Task").build();}
 */
public class SavvyTaskerBuilder {

    private SavvyTasker savvyTasker;

    public SavvyTaskerBuilder(SavvyTasker savvyTasker){
        this.savvyTasker = savvyTasker;
    }

    public SavvyTaskerBuilder withTask(Task task) throws DuplicateTaskException {
        savvyTasker.addTask(task);
        return this;
    }

    public SavvyTasker build(){
        return savvyTasker;
    }
}
