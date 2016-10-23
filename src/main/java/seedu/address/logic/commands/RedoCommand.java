package seedu.address.logic.commands;

import seedu.address.model.TaskBook;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    
    public static final String MESSAGE_REDO_TASK_SUCCESS = "Redid Task";
    
    private int numTimes;
    
    public RedoCommand() {};
    
    public RedoCommand(int numTimes) {
        this.numTimes = numTimes;
        
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        for (int i = 0; i < numTimes; i++) {
            TaskBook currentTaskBook = new TaskBook(model.getAddressBook());
            TaskBook toResetTo = redoStack.pop();
            model.resetData(toResetTo);
            undoStack.push(currentTaskBook);
        }
        return new CommandResult(MESSAGE_REDO_TASK_SUCCESS);
    }
}
