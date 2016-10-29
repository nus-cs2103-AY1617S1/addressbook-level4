package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueEventList;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;

/**
 * A utility class to help with building malitio objects.
 * Example usage: <br>
 *     {@code malitio ab = new malitioBuilder().withTask("Eat", "Sleep").withTag("Friend").build();}
 */
public class MalitioBuilder {

    private Malitio malitio;

    public MalitioBuilder(Malitio malitio) {
        this.malitio = malitio;
    }

    public MalitioBuilder withTask(FloatingTask task) throws DuplicateFloatingTaskException, DuplicateDeadlineException, DuplicateEventException {
        malitio.addTask(task);
        return this;
    }
    
    public MalitioBuilder withDeadline(Deadline deadline) throws DuplicateDeadlineException, DuplicateFloatingTaskException, DuplicateEventException {
        malitio.addTask(deadline);
        return this;
    }

    public MalitioBuilder withEvent(Event event) throws UniqueEventList.DuplicateEventException, DuplicateFloatingTaskException, DuplicateDeadlineException {
        malitio.addTask(event);
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
