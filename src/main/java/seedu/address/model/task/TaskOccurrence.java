package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;

//@@author A0135782Y
/** 
* This class served as the occurrence portion in an abstraction occurrence pattern.
* The abstraction is the Task and the occurrence is the TaskDateComponent.
*
*/
public class TaskOccurrence {

    private Task taskReference;
    private TaskDate startDate, endDate;
    private boolean isArchived;
    
    public TaskOccurrence(Task taskReference, TaskDate startDate, TaskDate endDate) {
        assert !CollectionUtil.isAnyNull(startDate, endDate);
        this.startDate = new TaskDate(startDate);
        this.endDate = new TaskDate(endDate);
        this.taskReference = taskReference;
    }
    
    public TaskOccurrence(TaskOccurrence taskDateComponent) {
        assert taskDateComponent != null : "Cannot pass in null values";
        this.taskReference = taskDateComponent.taskReference;
        this.startDate = taskDateComponent.startDate;
        this.endDate = taskDateComponent.endDate;
        this.isArchived = taskDateComponent.isArchived;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(TaskDate endDate) {
        this.endDate = endDate;
    }
    
    public void setTaskReferrence(Task task) {
    	this.taskReference = task;
    }
    
    public TaskDate getStartDate() {
        return startDate;
    }
    
    public TaskDate getEndDate() {
        return endDate;
    }
    
    //@@author A0147967J
    /**
     * Checks if TaskDateOccurrence is in a valid non-floating time.
     * 
     * @return True if it is in a valid time slot
     */
    public boolean isValidNonFloatingTime(){
        if(isSlot()){
            return (endDate.getDate()).after(startDate.getDate());
        }
        return true;
    }
    
    /**
     * Returns true if it is a blocked time slot
     */
    public boolean isBlockedSlot(){
        return taskReference.getName().fullName.equals(Name.DUMMY_NAME);
    }
    
    /**
     * Returns true if it is a valid time slot
     */
    public boolean isSlot(){
        return startDate.isPresent() && endDate.isPresent();
    }
    
    /**
     * Returns true if it is a deadline
     */
    public boolean isDeadline(){
        return !startDate.isPresent() && endDate.isPresent();
    }
    
    /** Returns true if this slot overlaps with the given one. */
    public boolean isOverlappedWith(TaskOccurrence given) {
        if(!isSlot()){
            return false;
        }
        return !(!given.getEndDate().getDate().after(startDate.getDate())
                || !given.getStartDate().getDate().before(endDate.getDate()));
    }
    
    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        builder.append(taskReference.getAsText());
        if(isSlot()){
            builder.append("\nFrom: "+startDate.getFormattedDate());
            builder.append(" To: "+endDate.getFormattedDate());
        }else if(isDeadline()){
            builder.append("\nBy: "+endDate.getFormattedDate());
        }else{
            
        }
        return builder.toString();
    }
    //@@author
    

    public ReadOnlyTask getTaskReference() {
        return taskReference;
    }

    /**
     * Archives this task component
     */
    public void archive() {
        isArchived = true;
    }
    
	//@@author A0147995H
    public void update(TaskDate startDate, TaskDate endDate) {
    	TaskDate realStartDate = startDate == null ? new TaskDate(TaskDate.DATE_NOT_PRESENT) : startDate;
    	TaskDate realEndDate = endDate == null ? new TaskDate(TaskDate.DATE_NOT_PRESENT) : endDate;
    	setStartDate(realStartDate);
    	setEndDate(realEndDate);
    }
    //@@author
    
    public boolean isArchived() {
        return isArchived;
    }
    
    private boolean isSameStateAs(TaskOccurrence other) {
        return other == this // short circuit if same object
            || (other != null // this is first to avoid NPE below
            && other.getTaskReference().getName().equals(this.getTaskReference().getName()) // state checks here onwards
            && other.getTaskReference().getTaskType().equals(this.getTaskReference().getTaskType())
            && other.getStartDate().equals(this.getStartDate())
            && other.getEndDate().equals(this.getEndDate())
            );
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskOccurrence // instanceof handles nulls
                && this.isSameStateAs((TaskOccurrence) other));        
    }
    
}
