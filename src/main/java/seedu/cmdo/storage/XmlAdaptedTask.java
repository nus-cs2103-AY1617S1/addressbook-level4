package seedu.cmdo.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.Done;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String detail;
    @XmlElement(required = true)
    private String done;
    @XmlElement(required = true)
    private String dueByDate;
    @XmlElement(required = true)
    private String dueByTime;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String block;

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
        detail = source.getDetail().details;
        done = source.checkDone().value.toString();
        dueByDate = source.getDueByDate().toString();
        dueByTime = source.getDueByTime().toString();
        priority = source.getPriority().value;
        block = String.valueOf(source.getBlock());
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     * 
     * @@author A0139661Y
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        
        // Settle the range first.
        final DueByDate dbd;
        final DueByTime dbt;
        
        String[] tokenizedDateRange = tokenizeTo(dueByDate);
        if (tokenizedDateRange.length == 2) {
        	dbd = new DueByDate(LocalDate.parse(tokenizedDateRange[0]), LocalDate.parse(tokenizedDateRange[1]));
        } else dbd = new DueByDate(LocalDate.parse(this.dueByDate));
        
        String[] tokenizedTimeRange = tokenizeTo(dueByTime);
        if (tokenizedTimeRange.length == 2) {
        	dbt = new DueByTime(LocalTime.parse(tokenizedTimeRange[0]), LocalTime.parse(tokenizedTimeRange[1]));
        } else dbt = new DueByTime(LocalTime.parse(this.dueByTime));
        
        // Settle the other parameters.
        final Detail detail = new Detail(this.detail);
        final Done done = new Done(Boolean.parseBoolean(this.done));
        final Priority priority = new Priority(this.priority);
        final boolean block = Boolean.parseBoolean(this.block);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(detail, done, dbd, dbt, priority, block, tags);
    }
    
    private String[] tokenizeTo(String input) {
    	return input.split("/to/");
    }
}
