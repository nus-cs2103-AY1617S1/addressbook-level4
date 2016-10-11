package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;

/**
 * A mutable floating task object. For testing only.
 */
public class TestFloatingTask implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    //private UniqueTagList tags;

    public TestFloatingTask() {
        try {
            this.priority = new Priority("5");
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    public void setPriorityValue(Priority priority){
        this.priority = priority;
    }
    
    @Override
    public Priority getPriorityValue() {
        return priority;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        sb.append("rank " + this.getPriorityValue().priorityValue);
        return sb.toString();
    }
}
