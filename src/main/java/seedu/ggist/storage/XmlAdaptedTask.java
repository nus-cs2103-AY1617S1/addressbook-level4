package seedu.ggist.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of GGist.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private boolean done;
    @XmlElement(required = true)
    private boolean deleted;
    @XmlElement(required = true)
    private String priority;

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
        taskName = source.getTaskName().taskName;
        startDate = source.getStartDate().value;
        startTime = source.getStartTime().value;
        endDate = source.getEndDate().value;
        endTime = source.getEndTime().value;
        done = source.isDone();
        priority = source.getPriority().value;
        if (source.isDeleted()) {
            endDate = "Sun, 23 Oct 16";
            endTime = "12:00 am";
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        
        final TaskName taskName = new TaskName(this.taskName);
        final TaskDate startDate = new TaskDate(this.startDate);
        final TaskTime startTime = new TaskTime(this.startTime);
        final TaskDate endDate = new TaskDate(this.endDate);
        final TaskTime endTime = new TaskTime(this.endTime);
        final boolean done = this.done;
        final Priority priority = new Priority(this.priority);
        Task newTask = new Task(taskName, startDate, startTime, endDate, endTime, priority);
        if (done) {
            newTask.setDone(); 
        }

        return newTask;
    }
}
