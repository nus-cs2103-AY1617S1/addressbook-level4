package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.RecurringTaskManager;
import seedu.address.logic.parser.Parser;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import java.time.LocalDate;
import java.time.ZoneId;
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
    @XmlElement
    private String recurringType;
    @XmlElement
    private boolean isArchived;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(TaskDateComponent source) {
        name = source.getTaskReference().getName().fullName;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTaskReference().getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        if (source.getTaskReference().getTaskType() == TaskType.NON_FLOATING) {
            startDate = source.getStartDate().getDateInLong();
            endDate = source.getEndDate().getDateInLong();
        }
        if (source.getTaskReference().getTaskType() == TaskType.FLOATING) {
            startDate = TaskDate.DATE_NOT_PRESENT;
            endDate = TaskDate.DATE_NOT_PRESENT;
        }
        if (source.getTaskReference().getRecurringType() != RecurringType.NONE) {
            handleRecurringTask(source.getStartDate(),
                    source.getEndDate(), 
                    source.getTaskReference().getRecurringType());
            startDate = source.getStartDate().getDateInLong();
            endDate = source.getEndDate().getDateInLong();
        }
        recurringType = source.getTaskReference().getRecurringType().name();
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
        RecurringType toBeAdded = RecurringType.NONE;
        if (recurringType != null ) {
            toBeAdded = RecurringType.valueOf(recurringType);
        }

        return new Task(name, tags, taskStartDate, taskEndDate, toBeAdded);
    }
    
    private void handleRecurringTask(TaskDate start, TaskDate end, RecurringType type) {
        LocalDate local;
        switch(type) {
            case DAILY:
                local = end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                local = local.plusDays(1);
                end.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local = start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    local = local.plusDays(1);
                    start.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());                    
                }
                break;
            case WEEKLY:
                local = end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                local = local.plusWeeks(1);
                end.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local = start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    local = local.plusWeeks(1);
                    start.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());                    
                }
                break;
            case MONTHLY:
                local = end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                local = local.plusMonths(1);
                end.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local = start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    local = local.plusMonths(1);
                    start.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());                    
                }                
                break;
            case YEARLY:
                local = end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                local = local.plusYears(1);
                end.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local = start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    local = local.plusYears(1);
                    start.setDateInLong(Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());                    
                }                
                break;
        }
    }
}
