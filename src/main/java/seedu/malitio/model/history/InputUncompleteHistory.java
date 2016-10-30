package seedu.malitio.model.history;

public class InputUncompleteHistory extends InputHistory {

    Object taskToComplete;
    
    public InputUncompleteHistory(Object taskToComplete) {
        this.taskToComplete = taskToComplete;
        this.commandForUndo = "complete";
    }
    
    public Object getTask() {
        return taskToComplete;
    }
}
