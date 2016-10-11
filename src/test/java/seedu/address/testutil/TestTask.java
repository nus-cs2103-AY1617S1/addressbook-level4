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
    private Priority priorityValue;   
    //private UniqueTagList tags;

    public TestTask() {
        this.priorityValue = Priority.MEDIUM;
        try {
            this.recurrenceRate = new RecurrenceRate(new Integer(0));
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.taskName = name;
    }

    @Override
    public Name getName() {
        return taskName;
    }
    
    public void setPriorityValue(Priority priority){
        this.priorityValue = priority;
    }
    
    @Override
    public Priority getPriorityValue() {
        return priorityValue;
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
    public RecurrenceRate getRecurrenceRate() {
        assert recurrenceRate != null;
        return recurrenceRate;
    }
}
