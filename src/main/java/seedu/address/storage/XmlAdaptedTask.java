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
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String start;
    @XmlElement(required = true)
    private String end;
    @XmlElement(required = true)
    private int taskCat;
    @XmlElement(required = true)
    private int overdue;
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
        name = source.getName().taskDetails;
        date = source.getDate().value;
        start = source.getStart().value;
        end = source.getEnd().value;
        taskCat = source.getTaskCategory();
        
        overdue = source.getOverdue();
        isCompleted = source.getIsCompleted();
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
        final Name name = new Name(this.name);
        final Date date = new Date(this.date);
        final Start start = new Start(this.start);
        final End end = new End(this.end);
        final int taskCat = this.taskCat;
        final int overdue = this.overdue;
        final boolean isCompleted = this.isCompleted;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, date, start, end, taskCat, overdue, isCompleted, tags);
    }
}
