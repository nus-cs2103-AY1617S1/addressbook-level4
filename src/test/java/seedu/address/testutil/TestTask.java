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
    private TaskDate startDate;
    private TaskDate endDate;
    private RecurringType recurringType;
    private List<TaskDateComponent> recurringDates;

    public TestTask() {
        tags = new UniqueTagList();
        startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        endDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        recurringType = RecurringType.NONE;
        recurringDates = new ArrayList<TaskDateComponent>();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setType(TaskType type){
    	this.type = type;
    }
    
    public void setStartDate(String date){
    	com.joestelmach.natty.Parser p = new Parser();
    	this.startDate = new TaskDate(p.parse(date).get(0).getDates().get(0).getTime());
    	this.type = TaskType.NON_FLOATING;
    }
    
    public void setEndDate(String date){
    	com.joestelmach.natty.Parser p = new Parser();
    	this.endDate = new TaskDate(p.parse(date).get(0).getDates().get(0).getTime());
    	this.type = TaskType.NON_FLOATING;
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
    public List<TaskDateComponent> getTaskDateComponent() {
        return recurringDates;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public TaskType getTaskType(){
    	return type;
    }
    
    @Override
    public TaskDate getStartDate(){
    	return startDate;
    }
    
    @Override
    public TaskDate getEndDate(){
    	return endDate;
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
        if(this.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT){
        	sb.append("by "+ this.getEndDate().getInputDate() + " ");
        }else{
        	sb.append("from "+ this.getStartDate().getInputDate() + " ");
        	sb.append("to "+ this.getEndDate().getInputDate() + " ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getBlockCommand() {
    	this.type = TaskType.NON_FLOATING;
        StringBuilder sb = new StringBuilder();
        sb.append("block ");
        sb.append("from "+ this.getStartDate().getInputDate() + " ");
        sb.append("to "+ this.getEndDate().getInputDate() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
