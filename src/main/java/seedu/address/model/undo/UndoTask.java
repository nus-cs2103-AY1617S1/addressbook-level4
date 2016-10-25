package seedu.address.model.undo;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

//@@author A0139145E
/**
 * Represents the data for Undo as a String command and Tasks initData and finalData
 * Only for EditCommand, finalData will not be null. 
 */
public class UndoTask {
    
    public String command;
    public Task initData;
    public Task finalData;
    
    /**
     * Initializes a UndoTask with the given variables
     * command, initData should not be null
     */
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
    
    /*
     * Returns the command stored
     */
    public String getCommand(){
        return command;
    }
    
    /*
     * Returns the initial Task stored
     */
    public Task getInitData(){
        return initData;
    }
    
    /*
     * Returns the final Task stored
     * Not null only when stored command is Edit 
     */
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
//@@author