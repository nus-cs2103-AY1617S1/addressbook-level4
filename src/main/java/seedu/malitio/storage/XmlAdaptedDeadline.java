package seedu.malitio.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyDeadline;

public class XmlAdaptedDeadline {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private boolean completed;
    @XmlElement(required = true)
    private boolean marked; 


    @XmlElement(required = true)
    private String due; 
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    //@@author A0129595N
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedDeadline() {}


    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedDeadline(ReadOnlyDeadline source) {
        name = source.getName().fullName;
        due = source.getDue().toString();
        completed = source.getCompleted();
        marked = source.isMarked();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Deadline toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final DateTime due = new DateTime(this.due);
        final boolean complete = this.completed;
        final boolean marked = this.marked;
        final UniqueTagList tags = new UniqueTagList(taskTags);
            return new Deadline(name, due, complete, marked, tags);
    }
}
