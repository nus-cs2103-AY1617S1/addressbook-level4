package seedu.address.testutil;

import com.joestelmach.natty.Parser;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask extends Task implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private TaskType type = TaskType.FLOATING;
    private TaskDate startDate;
    private TaskDate endDate;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setType(TaskType type){
    	this.type = type;
    }
    
    public void setStartDate(String date){
    	com.joestelmach.natty.Parser p = new Parser();
    	this.startDate = new TaskDate(p.parse(date).get(0).getDates().get(0));
    }
    
    public void setEndDate(String date){
    	com.joestelmach.natty.Parser p = new Parser();
    	this.endDate = new TaskDate(p.parse(date).get(0).getDates().get(0));
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
    public String toString() {
        return getAsText();
    }
    
    @Override
    public TaskType getType(){
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
        sb.append("from "+ this.getStartDate().getRawCommandInput() + " ");
        sb.append("to "+ this.getEndDate().getRawCommandInput() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
