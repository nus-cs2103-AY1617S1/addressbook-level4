package seedu.todo.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.*;
import seedu.todo.model.task.Recurrence.Frequency;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String detail;
    @XmlElement
    private String onDate;
    @XmlElement
    private String byDate;
    @XmlElement
    private String priority;
    @XmlElement
    private String completion;
    @XmlElement
    private String recurrence;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        detail = source.getDetail().toString();
        completion = source.getCompletion().toString();
        onDate = source.getOnDate().toString();
        byDate = source.getByDate().toString();
        priority = source.getPriority().toString();
        recurrence = source.getRecurrence().toString();
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
        final Detail detail = new Detail(this.detail);
        final Completion completion = new Completion(Boolean.parseBoolean(this.completion));
        final TaskDate onDate = new TaskDate(this.onDate, TaskDate.TASK_DATE_ON);
        final TaskDate byDate = new TaskDate(this.byDate, TaskDate.TASK_DATE_BY);
        final Priority priority = new Priority(this.priority);
        final Recurrence recurrence = new Recurrence(Frequency.valueOf(this.recurrence));
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, detail, completion, onDate, byDate, priority, recurrence, tags);
    }
}
