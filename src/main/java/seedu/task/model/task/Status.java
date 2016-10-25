package seedu.task.model.task;

public class Status {

    private boolean isDone;
    
    private boolean isOverdue;
    
    public Status(){
        this.isDone = false;
        this.isOverdue = false;
    }
    
    public Status(boolean isDone, boolean isOverdue){
        this.isDone = isDone;
        this.isOverdue = isOverdue;
    }
    
    public void setDoneStatus(boolean doneStatus){
        this.isDone = doneStatus;
    }
    
    public void setOverdueStatus(boolean overdueStatus){
        this.isDone = overdueStatus;
    }
    
    public boolean getDoneStatus(){
        return isDone;
    }
    
    public boolean getOverdueStatus(){
        return isOverdue;
    }
}
