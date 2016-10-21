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
    private String startDate;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String dueDate;
    @XmlElement(required = true)
    private boolean isCompleted;

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
        startDate = source.getStartDateTime().toString().replaceAll(":", "").replaceAll("-", "");
        description = source.getDescription().description;
        dueDate = source.getEndDateTime().toString().replaceAll(":", "").replaceAll("-", "");
        isCompleted = source.isCompleted();
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
        final DateTime startDateTime = new DateTime(this.startDate);
        final Description description = new Description(this.description);
        final DateTime endDateTime = new DateTime(this.dueDate);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, startDateTime, description, endDateTime, tags, this.isCompleted);
    }
}
