package seedu.malitio.model.history;

//@@author A0129595N
public class InputMarkHistory extends InputHistory {

    Object taskToMark;
    boolean markWhat;

    public InputMarkHistory(Object taskToMark, boolean marked) {
        this.commandForUndo = "unmark";
        this.taskToMark = taskToMark;
        if (marked) {
            this.markWhat = false;
        } else {
            this.markWhat = true;
        }
    }

    public boolean getMarkWhat() {
        return markWhat;
    }

    public Object getTaskToMark() {
        return taskToMark;
    }
}
