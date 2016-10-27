package harmony.mastermind.testutil;

import java.time.Duration;
import java.util.Date;

import guitests.TaskManagerGuiTest;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;

/**
 * A mutable task object. For testing only.
 */
//@@author A0124797R
public class TestTask implements ReadOnlyTask {
    
    private String name;
    private String startDate;
    private String endDate;
    private String recur;
    private UniqueTagList tags;
    private boolean isMarked;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    //@@author A0124797R
    public void setName(String name) {
        this.name = name;
    }
    
    //@@author A0124797R
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    //@@author A0124797R
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    //@@author A0124797R
    public void setRecur(String recur) {
        this.recur = recur;
    }


    //@@author A0138862W
    public String getAddCommand() {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add");

        cmd.append(" ").append(this.name);
        if(startDate != null && endDate !=null){
            cmd.append(" from ").append(startDate);
            cmd.append(" to ").append(endDate);
        }else if(endDate!=null){
            cmd.append(" from ").append(startDate);
            cmd.append(" to ").append(endDate);
        }
        
        
        cmd.append(" #");

        for (Tag t: tags) {
            cmd.append(t.tagName);
            cmd.append(',');
        }
        
        cmd.deleteCharAt(cmd.length()-1);

        return cmd.toString();
    }

    //@@author A0124797R
    @Override
    public String getName() {
        return name;
    }

    //@@author A0124797R
    @Override
    public Date getStartDate() {
        if (startDate!=null) {
            return TaskManagerGuiTest.prettyTimeParser.parse(startDate).get(0);
        }else {
            return null;
        }
    }

    //@@author A0124797R
    @Override
    public Date getEndDate() {
        if (endDate!=null) {
            return TaskManagerGuiTest.prettyTimeParser.parse(endDate).get(0);
        }else {
            return null;
        }
    }
    
    @Override
    //@@author A0124797R
    public String getRecur() {
        return recur;
    }
    
    @Override
    //@@author A0124797R
    public boolean isRecur() {
        return recur!=null;
    }

    @Override
    // @@author A0124797R
    public boolean isFloating() {
        return startDate == null && endDate == null;
    }

    @Override
    // @@author A0124797R
    public boolean isDeadline() {
        return startDate == null && endDate != null;
    }

    @Override
    // @@author A0124797R
    public boolean isEvent() {
        return startDate != null && endDate != null;
    }

    @Override
    //@@author A0124797R
    public boolean isMarked() {
        return this.isMarked;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    //@@author A0124797R
    @Override 
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.toString().equals(((Task) other).toString())); // state check
        
    }
    
    //@@author A0124797R
    @Override
    public boolean isSameTask(ReadOnlyTask task) {
        return this.toString().equals(((Task) task).toString());
    }
    
    //@@author A0124797R
    @Override
    public String toString() {
        return getAsText();
    }
    
    //@@author A0124797R
    public TestTask mark() {        
        this.isMarked = true;
        return this;
    }

    @Override
    public boolean isDue() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isHappening() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Duration getDueDuration() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Duration getEventDuration(){
        return null;
    }
    
}
