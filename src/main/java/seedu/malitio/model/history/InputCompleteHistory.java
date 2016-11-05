package seedu.malitio.model.history;

import seedu.malitio.logic.commands.UncompleteCommand;
//@@author A0129595N
public class InputCompleteHistory extends InputHistory {
    
    Object taskToUncomplete;
    
    public InputCompleteHistory(Object taskToUncomplete) {
        this.taskToUncomplete = taskToUncomplete;
        this.commandForUndo = UncompleteCommand.COMMAND_WORD;
    }
    
    public Object getTask() {
        return taskToUncomplete;
    }
}
