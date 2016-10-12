package seedu.todo.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.*;

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
    private String fromDate;
    @XmlElement
    private String tillDate;
    @XmlElement
    private String done;
    
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
        done = new Boolean(source.isDone()).toString();
        fromDate = source.getOnDate().toString();
        tillDate = source.getByDate().toString();
        
        
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
        final boolean done = new Boolean(this.done);
        final TaskDate fromDate = new TaskDate(this.fromDate);
        final TaskDate tillDate = new TaskDate(this.tillDate);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, detail, done, fromDate, tillDate, tags);
    }
}
