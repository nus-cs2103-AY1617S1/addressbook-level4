package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.LifeKeeper;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private LifeKeeper lifeKeeper;

    public AddressBookBuilder(LifeKeeper lifeKeeper){
        this.lifeKeeper = lifeKeeper;
    }

    public AddressBookBuilder withPerson(Activity activity) throws UniqueActivityList.DuplicateTaskException {
        lifeKeeper.addPerson(activity);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        lifeKeeper.addTag(new Tag(tagName));
        return this;
    }

    public LifeKeeper build(){
        return lifeKeeper;
    }
}
