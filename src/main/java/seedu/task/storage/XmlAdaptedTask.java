package seedu.task.storage;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;

import java.text.ParseException;
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
    private String startDate;
    @XmlElement(required = true)
    private String dueDate;
    @XmlElement(required = true)
    private String interval;
    @XmlElement(required = true)
    private String timeInterval;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String color;

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
   //@@author A0153751H
    public XmlAdaptedTask(ReadOnlyTask source) {
        title = source.getTitle().fullTitle;
        description =  source.getDescription().fullDescription;
        startDate = source.getStartDate().toString();
        dueDate = source.getDueDate().toString();
        interval = source.getInterval().toString();
        timeInterval = source.getTimeInterval().toString();
        status = source.getStatus().toString();
        color = source.getTaskColor().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     * @throws ParseException 
     */
    public Task toModelType() throws IllegalValueException, ParseException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        
        final Title title = new Title(this.title);
        final Description description = new Description(this.description);
        final StartDate startDate = new StartDate(this.startDate);
        final DueDate dueDate = new DueDate(this.dueDate);
        final Interval interval = new Interval(this.interval);
        final TimeInterval timeInterval = new TimeInterval(this.timeInterval);
        final Status status = new Status(this.status);
        final TaskColor color = new TaskColor(this.color);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title,description,startDate, dueDate,interval,timeInterval, status, color, tags);
    }
}
