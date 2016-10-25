package seedu.address.model.undo;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

public class UndoTask {
    
    public String command;
    public Task initData;
    public Task finalData;
    
    public UndoTask(String command, ReadOnlyTask initData, ReadOnlyTask finalData){
        this.command = command;
        this.initData = new Task(initData);
        if (finalData == null) {
            this.finalData = null;
        }
        else {
            this.finalData = new Task(finalData);
        }
    }
    
    public String getCommand(){
        return command;
    }
    
    public Task getInitData(){
        return initData;
    }
    
    public Task getFinalData(){
        return finalData;
    }
    
    @Override
    public String toString(){
        StringBuffer value = new StringBuffer();
        value.append(command).append(":").append(initData);
        if (finalData != null){
            value.append(" to ").append(finalData);
        }
        return value.toString();
    }
    
}
