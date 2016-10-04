package tars.testutil;

import tars.commons.exceptions.IllegalValueException;
import tars.model.Tars;
import tars.model.task.Task;
import tars.model.task.UniqueTaskList;
import tars.model.tag.Tag;

/**
 * A utility class to help with building Tars objects.
 * Example usage: <br>
 *     {@code Tars ab = new TarsBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TarsBuilder {

    private Tars tars;

    public TarsBuilder(Tars tars){
        this.tars = tars;
    }

    public TarsBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        tars.addTask(task);
        return this;
    }

    public TarsBuilder withTag(String tagName) throws IllegalValueException {
        tars.addTag(new Tag(tagName));
        return this;
    }

    public Tars build(){
        return tars;
    }
}
