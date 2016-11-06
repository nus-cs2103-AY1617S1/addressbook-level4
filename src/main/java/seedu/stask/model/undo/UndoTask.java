package seedu.stask.model.undo;

import seedu.stask.model.task.ReadOnlyTask;
import seedu.stask.model.task.Task;

//@@author A0139145E
/**
 * Represents the data for Undo as a String command and Tasks postData and preData
 * Only for EditCommand, preData will not be null. 
 */
public class UndoTask {
    
    public String command;
    public Task postData;
    public Task preData;
    
    /**
     * Initializes a UndoTask with the given variables
     * command, postData should not be null
     */
    public UndoTask(String command, ReadOnlyTask after, ReadOnlyTask before) {
        this.command = command;
        this.postData = new Task(after);
        if (before == null) {
            this.preData = null;
        } else {
            this.preData = new Task(before);
        }
    }
    
    /*
     * Returns the command stored
     */
    public String getCommand() {
        return command;
    }
    
    /*
     * Returns the initial Task stored
     */
    public Task getPostData() {
        return postData;
    }
    
    /*
     * Returns the final Task stored
     * Not null only when stored command is Edit 
     */
    public Task getPreData() {
        return preData;
    }
    
    @Override
    public String toString() {
        StringBuffer value = new StringBuffer();
        value.append(command).append(":").append(postData);
        if (preData != null) {
            value.append(" to ").append(preData);
        }
        return value.toString();
    }
}
//@@author