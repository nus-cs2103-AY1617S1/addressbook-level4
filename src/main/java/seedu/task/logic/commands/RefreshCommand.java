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

// @@author A0147335E
/**
 * Refresh the task manager.
 */
public class RefreshCommand extends Command {

    public static final String COMMAND_WORD = "refresh";
    public static final String MESSAGE_SUCCESS = "Task manager has been refreshed!";

    public RefreshCommand() {}

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        
        ArrayList<RollBackCommand> taskList = new ArrayList<RollBackCommand>();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        for(int i = 0; i < lastShownList.size(); i++) {
            ReadOnlyTask taskToDelete = lastShownList.get(i);
            
                Task task = new Task(taskToDelete.getName(), taskToDelete.getStartTime(), taskToDelete.getEndTime(), taskToDelete.getDeadline(), taskToDelete.getTags(), taskToDelete.getStatus());
                taskList.add(new RollBackCommand(COMMAND_WORD , task, null));
            
        }
        model.resetData(TaskManager.getEmptyTaskManager());
        
        int size = taskList.size() - 1; 

        while (!taskList.isEmpty()) {
            HashSet<Tag> tagSet = new HashSet<>(taskList.get(size).getNewTask().getTags().toSet());
            HashSet<String> tagStringSet = new HashSet<>(tagSet.size());
            for (Tag tags: tagSet) {
                tagStringSet.add(tags.tagName);
            }

            try {
                Command command = new AddCommand(
                        "" + taskList.get(size).getNewTask().getName(),
                        "" + taskList.get(size).getNewTask().getStartTime(),
                        "" + taskList.get(size).getNewTask().getEndTime(),
                        "" + taskList.get(size).getNewTask().getDeadline(),
                        tagStringSet);
                command.setData(model);
                command.execute(0);

            } catch (IllegalValueException e) {

            }
            taskList.remove(size);
            size--;
            if (size == -1) {
                break;
            }
            if (taskList.isEmpty()) {
                break;
            }
        }
        if (isUndo == false) {
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
