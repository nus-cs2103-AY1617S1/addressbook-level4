package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDescription(String name) throws IllegalValueException {
        this.task.setDescription(new Description(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withLocation(String address) throws IllegalValueException {
        this.task.setAddress(new Location(address));
        return this;
    }

    public TaskBuilder withDeadline(String date) throws IllegalValueException {
        this.task.setDate(Parser.parseDate(date));
        return this;
    }
//
//    public TaskBuilder withEmail(String email) throws IllegalValueException {
//        this.task.setEmail(new Email(email));
//        return this;
//    }

    public TestTask build() {
        return this.task;
    }

}
