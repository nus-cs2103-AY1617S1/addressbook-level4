package seedu.emeraldo.storage;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
import seedu.emeraldo.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String dateTime;
    //@@author A0139749L
    @XmlElement (required = true)
    private String completedDateTime = "";
    
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        description = source.getDescription().fullDescription;
        dateTime = source.getDateTime().value.toString();
        completedDateTime = source.getDateTime().completedDateTime_ToString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

System.out.println("Task " + completedDateTime); //TODO
        final Description description = new Description(this.description);
        final DateTime dateTime = new DateTime(this.dateTime, this.completedDateTime);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(description, dateTime, tags);
    }
}
