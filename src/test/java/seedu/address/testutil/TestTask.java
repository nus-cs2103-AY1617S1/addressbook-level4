package seedu.address.testutil;

import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;

/**
 * A mutable floating task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    protected Name taskName;
    private Date startDate;
    private Date endDate;
    private RecurrenceRate recurrenceRate;
    private Priority priority;   
    //private UniqueTagList tags;

    public TestTask() {
        this.priority = Priority.MEDIUM;
        this.recurrenceRate = null;
        //tags = new UniqueTagList();
    }
    
    /**
     * Copy constructor.
     * 
     * @throws IllegalValueException
     */
    public TestTask(ReadOnlyTask source) {
        Date tempStartDate = null, tempEndDate = null;
        if (source.getStartDate().isPresent()) {
            tempStartDate = source.getStartDate().get();
        }
        if (source.getEndDate().isPresent()) {
            tempEndDate = source.getEndDate().get();
        }

        this.taskName = source.getName();
        this.startDate = tempStartDate;
        this.endDate = tempEndDate;
        if (source.getRecurrenceRate().isPresent())
            this.recurrenceRate = source.getRecurrenceRate().get();
        else
            this.recurrenceRate = null;
        this.priority = source.getPriorityValue();
    }
    
    public void setName(Name name) {
        this.taskName = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPriority(Priority priorityValue) {
        this.priority = priorityValue;
    }

    public void setRecurrence(RecurrenceRate recurrenceRate) {
        this.recurrenceRate = recurrenceRate;
    }
    
    @Override
    public Name getName() {
        return taskName;
    }
    
    @Override
    public Priority getPriorityValue() {
        return priority;
    }

    @Override
    public Optional<Date> getStartDate() {
        if (startDate!=null) {
            return Optional.of(startDate);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Date> getEndDate() {
        if (endDate!=null) {
            return Optional.of(endDate);
        }
        return Optional.empty();    
    }

    @Override
    public Optional<RecurrenceRate> getRecurrenceRate() {
        if (recurrenceRate != null){
            return Optional.of(recurrenceRate);
        }
        return Optional.empty();
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        sb.append("-" + this.getPriorityValue().toString().toLowerCase());
        return sb.toString();
    }
}
