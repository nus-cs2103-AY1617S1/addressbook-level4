package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private long startDate;
    @XmlElement
    private long endDate;
    
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
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        if (source.getType() == TaskType.NON_FLOATING) {
            startDate = source.getStartDate().getDate();
            endDate = source.getEndDate().getDate();
        }
        if (source.getType() == TaskType.FLOATING) {
            startDate = TaskDate.DATE_NOT_PRESENT;
            endDate = TaskDate.DATE_NOT_PRESENT;
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
        final UniqueTagList tags = new UniqueTagList(taskTags);
        if (endDate != TaskDate.DATE_NOT_PRESENT) {
            return toModelTypeNonFloating(name, tags);
        }
        return toModelTypeFloating(name, tags);
    }


    private Task toModelTypeFloating(final Name name, final UniqueTagList tags) {
        return new Task(name, tags);
    }

    private Task toModelTypeNonFloating(final Name name, final UniqueTagList tags) {
        final TaskDate taskStartDate = new TaskDate(startDate);
        final TaskDate taskEndDate = new TaskDate(endDate);
        return new Task(name, tags, taskStartDate, taskEndDate);
    }
}
