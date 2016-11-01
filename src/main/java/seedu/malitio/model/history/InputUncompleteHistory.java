package seedu.malitio.model.history;

import seedu.malitio.logic.commands.CompleteCommand;

public class InputUncompleteHistory extends InputHistory {

    Object taskToComplete;
    
    public InputUncompleteHistory(Object taskToComplete) {
        this.taskToComplete = taskToComplete;
        this.commandForUndo = CompleteCommand.COMMAND_WORD;
    }
    
    public Object getTask() {
        return taskToComplete;
    }
}
