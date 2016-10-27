package seedu.address.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.task.*;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String address) throws IllegalValueException {
<<<<<<< .merge_file_lsXesP
        this.person.setAddress(new EndTime(address));
=======
        this.person.setEndTime(new EndTime(address));
>>>>>>> .merge_file_5vOuZE
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
<<<<<<< .merge_file_lsXesP
        this.person.setPhone(new Date(phone));
=======
        this.person.setDate(new Date(phone));
>>>>>>> .merge_file_5vOuZE
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
<<<<<<< .merge_file_lsXesP
        this.person.setEmail(new StartTime(email));
=======
        this.person.setStartTime(new StartTime(email));
>>>>>>> .merge_file_5vOuZE
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
