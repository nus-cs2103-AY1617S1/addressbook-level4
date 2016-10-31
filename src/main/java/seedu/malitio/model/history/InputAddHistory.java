package seedu.malitio.model.history;

import seedu.malitio.logic.commands.AddCommand;
import seedu.malitio.logic.commands.DeleteCommand;

//@@author A0129595N
public class InputAddHistory extends InputHistory {
    
    private Object task;
    
    public InputAddHistory(Object target) {
        this.task = target;
        this.commandForUndo = DeleteCommand.COMMAND_WORD;
        this.commandForRedo = AddCommand.COMMAND_WORD;
    }
        
    public Object getTask() {
        return task;
    }

}
