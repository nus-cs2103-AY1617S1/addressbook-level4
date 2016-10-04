package tars.testutil;

import tars.commons.exceptions.IllegalValueException;
import tars.model.Tars;
import tars.model.task.Person;
import tars.model.task.UniquePersonList;
import tars.model.tag.Tag;

/**
 * A utility class to help with building Tars objects.
 * Example usage: <br>
 *     {@code Tars ab = new TarsBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TarsBuilder {

    private Tars tars;

    public TarsBuilder(Tars tars){
        this.tars = tars;
    }

    public TarsBuilder withPerson(Person task) throws UniquePersonList.DuplicatePersonException {
        tars.addPerson(task);
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
