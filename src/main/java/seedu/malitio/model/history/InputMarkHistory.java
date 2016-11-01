package seedu.malitio.model.history;

import seedu.malitio.logic.commands.UnmarkCommand;

//@@author A0129595N
public class InputMarkHistory extends InputHistory {

    Object taskToMark;

    public InputMarkHistory(Object taskToMark) {
        this.commandForUndo = UnmarkCommand.COMMAND_WORD;
        this.taskToMark = taskToMark;
    }

    public Object getTask() {
        return taskToMark;
    }
}
