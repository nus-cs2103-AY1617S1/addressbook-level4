package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import com.joestelmach.natty.Parser;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask extends Task implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private TaskType type = TaskType.FLOATING;

    private RecurringType recurringType;
    private List<TaskComponent> recurringDates;

    public TestTask() {
        tags = new UniqueTagList();
        recurringType = RecurringType.NONE;
        recurringDates = new ArrayList<TaskComponent>();
        recurringDates.add(new TaskComponent(this ,new TaskDate(), new TaskDate()));
    }
    
    public TestTask(TestTask copy) {
        tags = new UniqueTagList(copy.tags);
        recurringType = RecurringType.NONE;
        recurringDates = new ArrayList<TaskComponent>();
        for(TaskComponent taskComponent: copy.recurringDates){
        	recurringDates.add(new TaskComponent(this, taskComponent.getStartDate(), taskComponent.getEndDate()));
        }
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setType(TaskType type){
    	this.type = type;
    }
    
    public void setTag(UniqueTagList tags){
    	this.tags = tags;
    }
    
    //Currently, only for non recurring tasks
    public void setStartDate(String date){
    	com.joestelmach.natty.Parser p = new Parser();
    	TaskDate startDate = new TaskDate(p.parse(date).get(0).getDates().get(0).getTime());
    	for(TaskComponent taskComponent: recurringDates)
    		taskComponent.setStartDate(startDate);
    	setType(TaskType.NON_FLOATING);
    }
    
    public void setEndDate(String date){
    	com.joestelmach.natty.Parser p = new Parser();
    	TaskDate endDate = new TaskDate(p.parse(date).get(0).getDates().get(0).getTime());
    	for(TaskComponent taskComponent: recurringDates)
    		taskComponent.setEndDate(endDate);
    	setType(TaskType.NON_FLOATING);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    @Override
    public RecurringType getRecurringType() {
        return recurringType;
    }
    
    @Override
    public List<TaskComponent> getTaskDateComponent() {
        return recurringDates;
    }
    
    @Override
	public TaskComponent getComponentForNonRecurringType() {
	    assert recurringDates.size() == 1 : "This method should only be used for non recurring tasks";
	    return recurringDates.get(0);
	}

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public TaskType getTaskType(){
    	return type;
    }

    public String getAddFloatingCommand() {
    	this.type = TaskType.FLOATING;
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getAddNonFloatingCommand() {
    	this.type = TaskType.NON_FLOATING;
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        if(this.getComponentForNonRecurringType().getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT){
        	sb.append("by "+ this.getComponentForNonRecurringType().getEndDate().getInputDate() + " ");
        }else{
        	sb.append("from "+ this.getComponentForNonRecurringType().getStartDate().getInputDate() + " ");
        	sb.append("to "+ this.getComponentForNonRecurringType().getEndDate().getInputDate() + " ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getBlockCommand() {
    	this.type = TaskType.NON_FLOATING;
        StringBuilder sb = new StringBuilder();
        sb.append("block ");
        sb.append("from "+ this.getComponentForNonRecurringType().getStartDate().getInputDate() + " ");
        sb.append("to "+ this.getComponentForNonRecurringType().getEndDate().getInputDate() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
