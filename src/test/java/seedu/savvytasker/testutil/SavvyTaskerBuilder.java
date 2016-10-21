package seedu.savvytasker.testutil;

import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;

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
