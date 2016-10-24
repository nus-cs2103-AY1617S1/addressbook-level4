package teamfour.tasc.logic.commands;

import teamfour.tasc.model.TaskList;
import teamfour.tasc.model.keyword.ClearCommandKeyword;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = ClearCommandKeyword.keyword;
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskList.getEmptyTaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public boolean canUndo() {
        return true;
    }

}
