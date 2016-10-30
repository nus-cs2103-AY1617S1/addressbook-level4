package seedu.malitio.model.history;

//@@author A0129595N
public class InputMarkHistory extends InputHistory {

    Object taskToMark;
    boolean markWhat = false;

    public InputMarkHistory(Object taskToMark) {
        this.commandForUndo = "unmark";
        this.taskToMark = taskToMark;
    }

    public boolean getMarkWhat() {
        return markWhat;
    }

    public Object getTask() {
        return taskToMark;
    }
}
