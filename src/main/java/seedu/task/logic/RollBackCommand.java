package seedu.task.logic;


import seedu.task.model.task.Task;


public class RollBackCommand {
    
    private String commandWord;
    private Task newTask;
    private Task oldTask;
    private int times;
    
    public RollBackCommand(String commandWord, Task newTask, Task oldTask){
        this.commandWord = commandWord;
        this.times = 1;
        this.newTask = newTask;
        this.oldTask = oldTask;
    }
    
    public RollBackCommand(String commandWord, Task newTask, Task oldTask, int times){
        this.commandWord = commandWord;
        this.times = times;
        this.newTask = newTask;
        this.oldTask = oldTask;
    }
    
    public void setCommandWord(String commandWord){
        this.commandWord = commandWord;
    }
    
    public void setNewTask(Task newTask){
        this.newTask = newTask;
    }
    
    public void setOldTask(Task oldTask){
        this.oldTask = oldTask;
    }
    
    public void setTimes(int times){
        this.times = times;
    }
    public String getCommandWord(){
        return commandWord;
    }
    public Task getNewTask(){
        return newTask;
    }
    public Task getOldTask(){
        return oldTask;
    }
    public int getTimes(){
        return times;
    }
    

}
