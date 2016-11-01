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
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
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
        
        if (recur != null) {
            cmd.append(recur);
        }

        return cmd.toString();
    }
    
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

    @Override
    public Date getEndDate() {
        if (endDate!=null) {
            return TaskManagerGuiTest.prettyTimeParser.parse(endDate).get(0);
        }else {
            return null;
        }
    }
    
    @Override
    public String getRecur() {
        return recur;
    }
    
    @Override
    public boolean isRecur() {
        return recur!=null;
    }

    @Override
    public boolean isFloating() {
        return startDate == null && endDate == null;
    }

    @Override
    public boolean isDeadline() {
        return startDate == null && endDate != null;
    }

    @Override
    public boolean isEvent() {
        return startDate != null && endDate != null;
    }

    @Override
    public boolean isMarked() {
        return this.isMarked;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    @Override 
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.toString().equals(((Task) other).toString())); // state check
        
    }
    
    @Override
    public boolean isSameTask(ReadOnlyTask task) {
        return this.toString().equals(((Task) task).toString());
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    public TestTask mark() {        
        this.isMarked = true;
        return this;
    }

    //@@author generated
    @Override
    public boolean isDue() {
        return false;
    }

    @Override
    public boolean isHappening() {
        return false;
    }

    @Override
    public Duration getDueDuration() {
        return null;
    }
    
    @Override
    public Duration getEventDuration(){
        return null;
    }

    @Override
    public Date getCreatedDate() {
        return null;
    }
    
    
    
}
