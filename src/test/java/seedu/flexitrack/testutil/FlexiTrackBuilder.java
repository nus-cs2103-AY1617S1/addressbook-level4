package seedu.flexitrack.testutil;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.tag.Tag;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;

/**
 * A utility class to help with building FlexiTrack objects. Example usage: <br>
 * TODO:
 * {@code FlexiTrack ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class FlexiTrackBuilder {

    private FlexiTrack flexiTrack;

    public FlexiTrackBuilder(FlexiTrack flexiTrack) {
        this.flexiTrack = flexiTrack;
    }

    public FlexiTrackBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        flexiTrack.addTask(person);
        return this;
    }

    public FlexiTrackBuilder withTag(String tagName) throws IllegalValueException {
        flexiTrack.addTag(new Tag(tagName));
        return this;
    }

    public FlexiTrack build() {
        return flexiTrack;
    }
}
