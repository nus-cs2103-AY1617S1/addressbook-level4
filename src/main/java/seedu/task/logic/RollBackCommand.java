//@@author A0147335E
package seedu.task.logic;

import seedu.task.model.task.Task;

/**
 * This class defines the the properties of a RollbackCommand object
 * to support undo command.
 * 
 *
 */
public class RollBackCommand {

    private String commandWord;
    private Task newTask;
    private Task oldTask;
    private int currentIndex;
    
    public RollBackCommand(String commandWord, Task newTask, Task oldTask) {
        this.commandWord = commandWord;
        this.newTask = newTask;
        this.oldTask = oldTask;
    }

    public RollBackCommand(String commandWord, Task newTask, Task oldTask,int currentIndex) {
        this.commandWord = commandWord;
        this.newTask = newTask;
        this.oldTask = oldTask;
        this.currentIndex = currentIndex;
        
    }
    
    public String getCommandWord() {
        return commandWord;
    }
    
    public Task getNewTask() {
        return newTask;
    }
    
    public Task getOldTask() {
        return oldTask;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
}
