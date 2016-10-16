package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniqueTaskList;

/**
 * A utility class to help with building Emeraldo objects.
 * Example usage: <br>
 *     {@code Emeraldo ab = new EmeraldoBuilder().withDescription("John", "Doe").withTag("Friend").build();}
 */
public class EmeraldoBuilder {

    private Emeraldo emeraldo;

    public EmeraldoBuilder(Emeraldo emeraldo){
        this.emeraldo = emeraldo;
    }

    public EmeraldoBuilder withDescription(Task task) throws UniqueTaskList.DuplicateTaskException {
        emeraldo.addTask(task);
        return this;
    }

    public EmeraldoBuilder withTag(String tagName) throws IllegalValueException {
        emeraldo.addTag(new Tag(tagName));
        return this;
    }

    public Emeraldo build(){
        return emeraldo;
    }
}
