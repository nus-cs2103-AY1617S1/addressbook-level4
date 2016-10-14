package harmony.mastermind.testutil;

import java.util.Date;

import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;

/**
 * A mutable task object. For testing only.
 */
//@@author A0124797R
public class TestTask implements ReadOnlyTask {
    
    private String name;
    private Date startDate;
    private Date endDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    //@@author A0124797R
    public void setName(String name) {
        this.name = name;
    }
    
    //@@author A0124797R
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    //@@author A0124797R
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    //@@author A0124797R
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add name/\"" + this.getName() + "\" ");
        sb.append("tags/");
        this.getTags().getInternalList().stream().forEach(s -> sb.append(s.tagName + ","));

        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
        
    }

    //@@author A0124797R
    @Override
    public String getName() {
        return name;
    }

    //@@author A0124797R
    @Override
    public Date getStartDate() {
        return startDate;
    }

    //@@author A0124797R
    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public boolean isFloating() {
        return false;
    }

    @Override
    public boolean isDeadline() {
        return false;
    }

    @Override
    public boolean isEvent() {
        return false;
    }

    @Override
    public boolean isMarked() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    //@@author A0124797R
    @Override 
    public boolean isSameTask(ReadOnlyTask task) {
        return getName().equals(task.getName());
    }
    
}
