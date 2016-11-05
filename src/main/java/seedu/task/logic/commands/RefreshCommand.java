// @@author A0147335E
package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;


/**
 * Refresh the task manager.
 */
public class RefreshCommand extends Command {

    public static final String COMMAND_WORD = "refresh";
    public static final String MESSAGE_SUCCESS = "Task manager has been refreshed!";
    public static final String EMPTY_STRING = "";

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;

        ArrayList<RollBackCommand> taskList = new ArrayList<RollBackCommand>();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        for (int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyTask taskToDelete = lastShownList.get(i);
            taskList.add(new RollBackCommand(COMMAND_WORD , (Task) taskToDelete, null));

        }
        model.resetData(TaskManager.getEmptyTaskManager());

        int index = taskList.size() - 1; 

        while (!taskList.isEmpty()) {
            HashSet<Tag> tagSet = new HashSet<>(taskList.get(index).getNewTask().getTags().toSet());
            HashSet<String> tagStringSet = new HashSet<>(tagSet.size());
            for (Tag tags: tagSet) {
                tagStringSet.add(tags.tagName);
            }

            try {
                
                Command command = new AddCommand(
                        EMPTY_STRING + taskList.get(index).getNewTask().getName(),
                        EMPTY_STRING + taskList.get(index).getNewTask().getStartTime(),
                        EMPTY_STRING + taskList.get(index).getNewTask().getEndTime(),
                        EMPTY_STRING + taskList.get(index).getNewTask().getDeadline(),
                        tagStringSet, taskList.get(index).getNewTask().getStatus());
                command.setData(model);
                command.execute(0);

            } catch (IllegalValueException e) {

            }
            taskList.remove(index);
            index = index - 1;
            if (index < 0) {
                break;
            }
            if (taskList.isEmpty()) {
                break;
            }
        }
        if (!isUndo) {
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, null, null));
        }
        // @author A0147944U-reused
        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();
        // @@author A0147335E
        return new CommandResult(MESSAGE_SUCCESS);
    }

    

    @Override
    public CommandResult execute(int index) {
        return null;
    }
}
