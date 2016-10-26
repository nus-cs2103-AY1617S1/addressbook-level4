package seedu.malitio.model.history;

import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

public class InputMarkHistory extends InputHistory {
    
    ReadOnlyFloatingTask taskToMark;
    ReadOnlyDeadline deadlineToMark;
    boolean markWhat;
    String type;

    public InputMarkHistory(ReadOnlyFloatingTask taskToMark, boolean marked) {
        this.commandForUndo = "mark";
        this.type = "floating task";
        this.taskToMark = taskToMark;
        if (marked) {
            this.markWhat = false;
        }
        else {
            this.markWhat = true;
        }
    }

    public InputMarkHistory(ReadOnlyDeadline deadlineToMark, boolean marked) {
        this.commandForUndo = "mark";
        this.type = "deadline";
        this.deadlineToMark = deadlineToMark;
        if (marked) {
            this.markWhat = false;
        }
        else {
            this.markWhat = true;
        }
    }
    
    public String getType() {
        return type;
    }
    
    public ReadOnlyFloatingTask getTaskToMark() {
        return taskToMark;
    }
    
    public ReadOnlyDeadline getDeadlineToMark() {
        return deadlineToMark;
    }
    
    public boolean getMarkWhat() {
        return markWhat;
    }
}
