package seedu.agendum.storage;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.tag.Tag;
import seedu.agendum.model.tag.UniqueTagList;
import seedu.agendum.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String isCompleted;
    @XmlElement(required = false)
    private String startDateTime;
    @XmlElement(required = false)
    private String endDateTime;

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
        name = source.getName().fullName;
        isCompleted = Boolean.toString(source.isCompleted());
        tagged = new ArrayList<>();
        if (source.getStartDateTime().isPresent()) {
            startDateTime = source.getStartDateTime().get().format(formatter);
        }
        if (source.getEndDateTime().isPresent()) {
            endDateTime = source.getEndDateTime().get().format(formatter);
        }
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
        final Name name = new Name(this.name);
        final boolean markedAsCompleted = Boolean.valueOf(isCompleted);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        
        Task newTask = new Task(name,tags);
        if (markedAsCompleted) {
            newTask.markAsCompleted();
        }
        if (startDateTime != null) {
            newTask.setStartDateTime(Optional.ofNullable(LocalDateTime.parse(this.startDateTime, formatter)));
        }
        if (endDateTime != null) {
            newTask.setEndDateTime(Optional.ofNullable(LocalDateTime.parse(this.endDateTime, formatter)));
        }
        return newTask;
    }
}
