package seedu.task.model.task;

public class Status {

    private boolean isDone;
    
    private boolean isOverdue;
    
    private boolean newlyAdded;
    
    public Status(){
        this.isDone = false;
        this.isOverdue = false;
        this.newlyAdded = false;
    }
    
    public Status(boolean isDone, boolean isOverdue, boolean newlyAdded){
        this.isDone = isDone;
        this.isOverdue = isOverdue;
        this.newlyAdded = newlyAdded;
    }
    
    public void setDoneStatus(boolean doneStatus){
        this.isDone = doneStatus;
    }
    
    public void setOverdueStatus(boolean overdueStatus){
        this.isDone = overdueStatus;
    }
    
    public void setNewlyAdded(boolean newlyAdded){
        this.newlyAdded = newlyAdded;
    }
    
    public boolean getDoneStatus(){
        return isDone;
    }
    
    public boolean getOverdueStatus(){
        return isOverdue;
    }
    
    public boolean getNewlyAddedStatus(){
        return newlyAdded;
    }
}
