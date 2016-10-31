package seedu.malitio.model.history;

import seedu.malitio.logic.commands.MarkCommand;
import seedu.malitio.logic.commands.UnmarkCommand;

public class InputUnmarkHistory extends InputHistory {
    
    Object taskToUnmark;
    boolean markWhat = true;

    public InputUnmarkHistory(Object taskToUnmark) {
        this.commandForUndo = MarkCommand.COMMAND_WORD;
        this.commandForRedo = UnmarkCommand.COMMAND_WORD;
        this.taskToUnmark = taskToUnmark;
    }

    public boolean getMarkWhat() {
        return markWhat;
    }

    public Object getTask() {
        return taskToUnmark;
    }
}
