package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.Task;
import seedu.malitio.model.task.UniqueTaskList;

/**
 * A utility class to help with building malitio objects.
 * Example usage: <br>
 *     {@code malitio ab = new malitioBuilder().withTask("Eat", "Sleep").withTag("Friend").build();}
 */
public class MalitioBuilder {

    private Malitio malitio;

    public MalitioBuilder(Malitio malitio){
        this.malitio = malitio;
    }

    public MalitioBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        malitio.addTask(task);
        return this;
    }

    public MalitioBuilder withTag(String tagName) throws IllegalValueException {
        malitio.addTag(new Tag(tagName));
        return this;
    }

    public Malitio build(){
        return malitio;
    }
}
