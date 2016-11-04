package seedu.task.logic;

import seedu.task.model.task.Task;

/**
 * This class defines the the properties of a RollbackCommand object
 * to support undo command.
 * 
 * @@author A0147335E
 *
 */
public class RollBackCommand {

    private String commandWord;
    private Task newTask;
    private Task oldTask;

    public RollBackCommand(String commandWord, Task newTask, Task oldTask) {
        this.commandWord = commandWord;
        this.newTask = newTask;
        this.oldTask = oldTask;
    }

    public void setCommandWord(String commandWord) {
        this.commandWord = commandWord;
    }

    public void setNewTask(Task newTask) {
        this.newTask = newTask;
    }

    public void setOldTask(Task oldTask) {
        this.oldTask = oldTask;
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
}
