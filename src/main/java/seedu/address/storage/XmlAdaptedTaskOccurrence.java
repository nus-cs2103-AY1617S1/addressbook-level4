package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTaskOccurrence {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    //@@author A0135782Y
    @XmlElement
    private long startDate;
    @XmlElement
    private long endDate;
    @XmlElement
    private String recurringType;
    @XmlElement
    private int recurringPeriod;
    @XmlElement
    private boolean isArchived;
    //@@author
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTaskOccurrence() {}

    //@@author A0135782Y
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTaskOccurrence(TaskOccurrence source) {
        name = source.getTaskReference().getName().fullName;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTaskReference().getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        handleDatesByTaskType(source);
        handleDatesByRecurringType(source);
        recurringType = source.getTaskReference().getRecurringType().name();
        recurringPeriod = source.getTaskReference().getRecurringPeriod();
        isArchived = source.isArchived();
    }

    /**
     * Saves dates only if it has been archived.
     */
    private void handleDatesByRecurringType(TaskOccurrence source) {
        if (source.getTaskReference().getRecurringType() != RecurringType.NONE && source.isArchived()) {
            TaskDate startCopy = new TaskDate(source.getStartDate());
            TaskDate endCopy = new TaskDate(source.getEndDate());
            startDate = startCopy.getDateInLong();
            endDate = endCopy.getDateInLong();
        }
    }

    /**
     * Saves dates based on the task type of the task occurrence.
     */
    private void handleDatesByTaskType(TaskOccurrence source) {
        if (source.getTaskReference().getTaskType() == TaskType.NON_FLOATING 
                || source.getTaskReference().getTaskType() == TaskType.COMPLETED) {
            startDate = source.getStartDate().getDateInLong();
            endDate = source.getEndDate().getDateInLong();
        } else {
            startDate = TaskDate.DATE_NOT_PRESENT;
            endDate = TaskDate.DATE_NOT_PRESENT;
        }
    }
    
    /**
     * Converts this jaxb-friendly adapted task occurrence object into the model's TaskOccurrence object.
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

    /**
     * Converts this jaxb-friendly adapted task occurrence object into the model's TaskOccurrence object for floating tasks.
     */
    private Task toModelTypeFloating(final Name name, final UniqueTagList tags) {
    	Task task = new Task(name, tags);
    	
    	if(isArchived){
    		task.setTaskType(TaskType.COMPLETED);
        	for(TaskOccurrence t: task.getTaskDateComponent()){
        		t.archive();
        	}
        }
        return task;
    }

    /**
     * Converts this jaxb-friendly adapted task occurrence object into the model's TaskOccurrence object for non floating tasks.
     */
    private Task toModelTypeNonFloating(final Name name, final UniqueTagList tags) {
        final TaskDate taskStartDate = new TaskDate(startDate);
        final TaskDate taskEndDate = new TaskDate(endDate);
        RecurringType toBeAdded = RecurringType.NONE;
        if (recurringType != null ) {
            toBeAdded = RecurringType.valueOf(recurringType);
        }
        Task task = new Task(name, tags, taskStartDate, taskEndDate, toBeAdded, recurringPeriod);
        if(isArchived){
        	task.setTaskType(TaskType.COMPLETED);
        	for(TaskOccurrence t: task.getTaskDateComponent()){
        		t.archive();
        	}
        }

        return task;
    }
    //@@author
}
