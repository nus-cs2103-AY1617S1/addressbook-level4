package seedu.malitio.model.history;

import seedu.malitio.logic.commands.CompleteCommand;
import seedu.malitio.logic.commands.UncompleteCommand;

public class InputUncompleteHistory extends InputHistory {

    Object taskToComplete;
    
    public InputUncompleteHistory(Object taskToComplete) {
        this.taskToComplete = taskToComplete;
        this.commandForUndo = CompleteCommand.COMMAND_WORD;
        this.commandForRedo = UncompleteCommand.COMMAND_WORD;
    }
    
    public Object getTask() {
        return taskToComplete;
    }
}
