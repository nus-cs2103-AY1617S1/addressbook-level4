package seedu.malitio.model.history;

import seedu.malitio.logic.commands.MarkCommand;
import seedu.malitio.logic.commands.UnmarkCommand;

//@@author A0129595N
public class InputMarkHistory extends InputHistory {

    Object taskToMark;
    boolean markWhat = false;

    public InputMarkHistory(Object taskToMark) {
        this.commandForUndo = UnmarkCommand.COMMAND_WORD;
        this.commandForRedo = MarkCommand.COMMAND_WORD;
        this.taskToMark = taskToMark;
    }

    public boolean getMarkWhat() {
        return markWhat;
    }

    public Object getTask() {
        return taskToMark;
    }
}
