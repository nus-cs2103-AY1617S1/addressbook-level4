package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;

public class TaskDateComponent {
    private TaskDate startDate, endDate;
    
    public TaskDateComponent() {
        this.startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        this.endDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
    }
    
    public TaskDateComponent(TaskDate startDate, TaskDate endDate) {
        assert !CollectionUtil.isAnyNull(startDate, endDate);
        this.startDate = new TaskDate(startDate);
        this.endDate = new TaskDate(endDate);
    }
    
    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(TaskDate endDate) {
        this.endDate = endDate;
    }
    
    public TaskDate getStartDate() {
        return startDate;
    }
    
    public TaskDate getEndDate() {
        return endDate;
    }
    
    public boolean isValidTimeSlot(){
        if(startDate!=null && endDate!=null){
            return (endDate.getDate()).after(startDate.getDate());
        }else{
            return true;
        }
    }
    
    public boolean hasOnlyEndDate() {
        if (startDate.getDateInLong() != TaskDate.DATE_NOT_PRESENT){
            return false;
        }
        return true;
    }
    
}
