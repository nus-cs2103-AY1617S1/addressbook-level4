package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.Task;
import seedu.malitio.model.task.UniqueTaskList;

/**
 * A utility class to help with building malitio objects.
 * Example usage: <br>
 *     {@code malitio ab = new malitioBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class malitioBuilder {

    private Malitio malitio;

    public malitioBuilder(Malitio malitio){
        this.malitio = malitio;
    }

    public malitioBuilder withPerson(Task task) throws UniqueTaskList.DuplicateTaskException {
        malitio.addTask(task);
        return this;
    }

    public malitioBuilder withTag(String tagName) throws IllegalValueException {
        malitio.addTag(new Tag(tagName));
        return this;
    }

    public Malitio build(){
        return malitio;
    }
}
