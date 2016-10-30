package harmony.mastermind.model.task;


//@@author A0138862W
import java.util.Date;
import java.util.Set;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.tag.UniqueTagList.DuplicateTagException;

/**
 * The TaskBuilder is a safe way to create a task object instead of relying on constructors.
 * The Task has numerous attributes which depends on the nature of the task (event, floating, deadline),
 * it must be build independently.
 * <br/>
 * The task builder provide a systematic way to build a task object and is future proof.
 * Task Builder mitigates the problem of having too many parameters in Task constructor.
 *
 */
public class TaskBuilder {

    private final String name;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private UniqueTagList tags;
    private String recur;
    private boolean isMarked;
    
    /**
     * creation date will automatically assigned upon initializing.
     * 
     * @param name is mandatory field. The task name.
     * 
     */
    public TaskBuilder(String name){
        this.name = name;
        this.createdDate = new Date();
    }
    
    /**
     * build a floating task with both start and end dates are null
     * 
     */
    public TaskBuilder asFloating(){
        startDate = null;
        endDate = null;
        return this;
    }
    
    /**
     * build an event with both valid start and end dates
     * 
     * @param startDate is the start date of the event
     * @param endDate is the end date of the event
     * 
     * @throws InvalidEventDateException if start date is after end date
     * 
     */
    public TaskBuilder asEvent(Date startDate, Date endDate) throws InvalidEventDateException{
        if (startDate.after(endDate)) {
            throw new InvalidEventDateException();
        }
        
        this.startDate = startDate;
        this.endDate = endDate;
        
        return this;
    }
    
    /**
     * build a deadline task with only end date
     * 
     *  @param endDate is the due date.
     * 
     */
    public TaskBuilder asDeadline(Date endDate){
        this.startDate = null;
        this.endDate = endDate;
        return this;
    }
    
    public TaskBuilder withTags(Set<String> tagSet) throws IllegalValueException {
        tags = new UniqueTagList(); 
        for (String tag: tagSet) {
            this.tags.add(new Tag(tag));
        }
        return this;
    }
    
    /**
     * build a recurring task. 
     * 
     * @param recur can be daily, weekly, monthly, yearly
     * 
     */
    public TaskBuilder asRecurring(String recur){
        this.recur = recur;
        return this;
    }
    
    /**
     * Set a custom creation date. This will overwrite the auto assigned date.
     * 
     * @param createdDate is the customized creation date
     * 
     */
    public TaskBuilder withCreationDate(Date createdDate){
        this.createdDate = createdDate;
        return this;
    }
    
    /**
     * build a task as marked task
     * 
     */
    public TaskBuilder asMarked(){
        this.isMarked = true;
        return this;
    }
    
    
    /**
     * finalize build and return a Task object
     * 
     */
    public Task build(){
        return new Task(this);
    }

    //@@author generated
    public String getName() {
        return name;
    }
    //@@author generated
    public Date getStartDate() {
        return startDate;
    }
    //@@author generated
    public Date getEndDate() {
        return endDate;
    }
    //@@author generated
    public Date getCreatedDate() {
        return createdDate;
    }
    //@@author generated
    public UniqueTagList getTags() {
        return tags;
    }
    //@@author generated
    public String getRecur() {
        return recur;
    }
    //@@author generated
    public boolean isMarked() {
        return isMarked;
    }
    
    
    
}
