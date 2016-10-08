package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String detail;
    @XmlElement
    private String fromDate;
    @XmlElement
    private String tillDate;


    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        detail = source.getDetail().toString();
        fromDate = source.getFromDate().toString();
        tillDate = source.getTillDate().toString();

    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();

        final Name name = new Name(this.name);
        final Detail detail = new Detail(this.detail);
        final TaskDate fromDate = new TaskDate(this.fromDate);
        final TaskDate tillDate = new TaskDate(this.tillDate);
        return new Task(name, detail, fromDate, tillDate);
    }
}
