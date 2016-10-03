package tars.testutil;

import tars.commons.exceptions.IllegalValueException;
import tars.model.Tars;
import tars.model.person.Person;
import tars.model.person.UniquePersonList;
import tars.model.tag.Tag;

/**
 * A utility class to help with building Tars objects.
 * Example usage: <br>
 *     {@code Tars ab = new TarsBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TarsBuilder {

    private Tars addressBook;

    public TarsBuilder(Tars addressBook){
        this.addressBook = addressBook;
    }

    public TarsBuilder withPerson(Person person) throws UniquePersonList.DuplicatePersonException {
        addressBook.addPerson(person);
        return this;
    }

    public TarsBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public Tars build(){
        return addressBook;
    }
}
