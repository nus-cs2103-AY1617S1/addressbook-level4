package seedu.address.logic.commands;

import seedu.address.model.TaskBook;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undid Task";

    private int numTimes;

    public UndoCommand() {
    }

    public UndoCommand(int numTimes) {
        this.numTimes = numTimes;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        for (int i = 0; i < numTimes; i++) {
            TaskBook currentTaskBook = new TaskBook(model.getAddressBook());
            TaskBook toResetTo = undoStack.pop();
            model.resetData(toResetTo);
            redoStack.push(currentTaskBook);
        }
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
}
