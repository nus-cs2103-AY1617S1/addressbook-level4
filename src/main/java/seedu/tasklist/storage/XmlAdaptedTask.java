package seedu.tasklist.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String recurringFrequency;
    @XmlElement(required = true)
    private String isComplete;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getTaskDetails().taskDetails;
        startTime = Long.toString(source.getStartTime().time.getTimeInMillis());
        endTime = Long.toString(source.getEndTime().time.getTimeInMillis());
        priority = source.getPriority().priorityLevel;
        recurringFrequency = source.getRecurringFrequency();
        isComplete = String.valueOf(source.isComplete());
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final TaskDetails name = new TaskDetails(this.name);
        final StartTime startTime = new StartTime(Long.valueOf(this.startTime));
        final EndTime endTime = new EndTime(Long.valueOf(this.endTime));
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);

        final boolean isComplete = Boolean.valueOf(this.isComplete);
        Task newTask = new Task(name, startTime, endTime, priority, tags, recurringFrequency);
        if(isComplete){
        	newTask.markAsComplete();
        }
        return newTask;
    }
}
