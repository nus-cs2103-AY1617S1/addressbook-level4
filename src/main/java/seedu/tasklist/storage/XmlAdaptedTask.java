package seedu.tasklist.storage;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private boolean isCompleted;
    @XmlElement(required = true)
    private boolean isOverdue;
    @XmlElement(required = true)
    private boolean isFloating;

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
        title = source.getTitle().fullTitle;
        description = source.getDescription().description;
        startDateTime = source.getStartDateTime().toString().replaceAll(":", "").replaceAll("-", "");
        endDateTime = source.getEndDateTime().toString().replaceAll(":", "").replaceAll("-", "");
        isCompleted = source.isCompleted();
        isOverdue = source.isOverdue();
        isFloating = source.isFloating();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Description description = new Description(this.description);
        final DateTime startDateTime = new DateTime(this.startDateTime);
        final DateTime endDateTime = new DateTime(this.endDateTime);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, startDateTime, description, endDateTime, tags, this.isCompleted, this.isOverdue, this.isFloating);
    }
}
