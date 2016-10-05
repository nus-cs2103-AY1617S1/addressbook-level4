package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.ActivityManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ActivityManager ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private ActivityManager activityManager;

    public AddressBookBuilder(ActivityManager activityManager){
        this.activityManager = activityManager;
    }

    public AddressBookBuilder withPerson(Person person) throws UniquePersonList.DuplicatePersonException {
        activityManager.addPerson(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        activityManager.addTag(new Tag(tagName));
        return this;
    }

    public ActivityManager build(){
        return activityManager;
    }
}
