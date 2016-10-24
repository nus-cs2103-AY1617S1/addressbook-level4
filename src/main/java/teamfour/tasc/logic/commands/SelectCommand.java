package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.Messages;
import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.model.keyword.SelectCommandKeyword;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = SelectCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    public static final String MESSAGE_SELECT_EMPTY_LIST = "Can't select from an empty list";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if(targetIndex == -1){ //Indicates a select last command
            int listSize = model.getFilteredTaskList().size();
            if(listSize < 1){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(MESSAGE_SELECT_EMPTY_LIST);
            }
            EventsCenter.getInstance().post(new JumpToListRequestEvent(listSize - 1));
            model.updateFilteredTaskListByFilter(); //refresh the list view
            return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, listSize));
        }

        if (lastShownList.size() < targetIndex) {
            if(lastShownList.size() < 1){
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + "\n" + MESSAGE_SELECT_EMPTY_LIST);
            }
            else{
                String validIndexRange = "Valid index range: 1 to " + lastShownList.size();
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + "\n" + validIndexRange);
            }
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        model.updateFilteredTaskListByFilter(); //refresh the list view
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
