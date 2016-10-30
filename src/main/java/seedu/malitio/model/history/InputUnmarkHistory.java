package seedu.malitio.model.history;

public class InputUnmarkHistory extends InputHistory {
    
    Object taskToUnmark;
    boolean markWhat = true;

    public InputUnmarkHistory(Object taskToUnmark) {
        this.commandForUndo = "mark";
        this.taskToUnmark = taskToUnmark;
    }

    public boolean getMarkWhat() {
        return markWhat;
    }

    public Object getTask() {
        return taskToUnmark;
    }
}
