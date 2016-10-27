package harmony.mastermind.model.task;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import harmony.mastermind.commons.exceptions.DuplicateDataException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;

public class Task implements ReadOnlyTask {

    private String name;
    private Date startDate;
    private Date endDate;
    private UniqueTagList tags;
    private String recur;
    private boolean marked;

    
    // event
    // @@author A0138862W
    public Task(String name, Date startDate, Date endDate, UniqueTagList tags, String recur) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.marked = false;
        this.recur = recur;
    }

    // deadline
    // @@author A0138862W
    public Task(String name, Date endDate, UniqueTagList tags, String recur) {
        this(name, null, endDate, tags, recur);
    }

    // floating
    // @@author A0138862W
    public Task(String name, UniqueTagList tags) {
        this(name, null, null, tags, null);
    }

    // @@author A0138862W
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getTags(), source.getRecur());
        this.marked = source.isMarked();
    }

    @Override
    // @@author generated
    public String getName() {
        return name;
    }

    // @@author generated
    public void setName(String name) {
        this.name = name;
    }

    @Override
    // @@author generated
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    // @@author generated
    public Date getStartDate() {
        return startDate;
    }

    // @@author generated
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    // @@author generated
    public Date getEndDate() {
        return endDate;
    }

    // @@author generated
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // @@author generated
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    @Override
    //@@author A0124797R
    public String getRecur() {
        return this.recur;
    }
    
    @Override
    ///@@author A0124797R
    public boolean isRecur() {
        return recur != null;
    }

    @Override
    // @@author A0138862W
    public boolean isFloating() {
        return startDate == null && endDate == null;
    }

    @Override
    // @@author A0138862W
    public boolean isDeadline() {
        return startDate == null && endDate != null;
    }

    @Override
    // @@author A0138862W
    public boolean isEvent() {
        return startDate != null && endDate != null;
    }

    //@@author A0124797R
    @Override 
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.toString().equals(((Task) other).toString())); // state check
        
    }
        
    @Override
    //@@author A0124797R
    public boolean isMarked() {
        return this.marked;
    }

    //@@author A0124797R
    public Task mark() {
        this.marked = true;
        return this;
    }
    
    //@@author A0124797R
    public Task unmark() {
        this.marked = false;
        return this;
    }
    
    //@@author A0124797R
    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0138862W
    public boolean isDue(){        
        if(isDeadline()){
            Date now = new Date();
            if(now.after(endDate)){
                return true;
            }else{
                return false;
            }
        }else if(isEvent()){
            Date now = new Date();
            if(now.after(startDate) && now.after(endDate)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //@@author A0138862W
    public boolean isHappening(){
        if(isEvent()){
            Date now = new Date();
            if(now.after(startDate) && now.before(endDate)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    // @@author A0138862W
    public Duration getEventDuration(){
        if(isEvent()){
            long differencel = endDate.getTime() - startDate.getTime();
            
            return Duration.of(differencel, ChronoUnit.MILLIS);
        }else{
            return null;
        }
    }
    
    /*
     * calculate the duration until due date
     * 
     */
    //@@author A0138862W
    public Duration getDueDuration(){
        if(endDate != null){
            long nowl = System.currentTimeMillis();
            long endDatel = endDate.getTime();
            
            long differencel = endDatel - nowl;
            
            return Duration.of(differencel, ChronoUnit.MILLIS);
        }else{
            return null;
        }
    }
}
