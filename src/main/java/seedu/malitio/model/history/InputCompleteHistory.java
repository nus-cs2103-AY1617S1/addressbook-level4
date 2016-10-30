package seedu.malitio.model.history;

public class InputCompleteHistory extends InputHistory {
    
    Object taskToUncomplete;
    
    public InputCompleteHistory(Object taskToUncomplete) {
        this.taskToUncomplete = taskToUncomplete;
        this.commandForUndo = "uncomplete";
    }
    
    public Object getTask() {
        return taskToUncomplete;
    }
}
