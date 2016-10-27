package harmony.mastermind.storage;

import javax.xml.bind.annotation.XmlElement;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JAXB-friendly version of the Task.
 */
//@@author A0124797R
public class XmlAdaptedDeadline {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private Date endDate;
    @XmlElement(required = true)
    private String recur;
    @XmlElement(required = true)
    private Date createdDate;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedDeadline() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDeadline
     */
    public XmlAdaptedDeadline(ReadOnlyTask source) {
        name = source.getName();
        endDate = source.getEndDate();
        recur = source.getRecur();
        createdDate = source.getCreatedDate();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted deadline object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deadline
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        
        final String name = this.name;
        final Date endDate = this.endDate;
        final String recur = this.recur;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        
        return new Task(name, endDate, tags, recur, createdDate);
    }
}
