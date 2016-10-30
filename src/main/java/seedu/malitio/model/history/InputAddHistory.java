package seedu.malitio.model.history;
//@@author A0129595N
public class InputAddHistory extends InputHistory {
    
    private Object task;
    
    public InputAddHistory(Object target) {
        this.task = target;
        this.commandForUndo = "delete";
    }
        
    public Object getTask() {
        return task;
    }

}
