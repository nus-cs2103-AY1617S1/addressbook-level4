package seedu.address.logic.commands;


import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

public class DoneCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String TOOL_TIP = "done INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_DONE_ITEM_SUCCESS = "Archived Item: %1$s";

    // temporary TODO
    public static final String MESSAGE_DONE_UNDO_SUCCESS = "Undid archive tasks! Tasks restored to undone list!";

    
    private List<Task> doneTasks;
    
    private List<Task> doneTasksUndoFail;

    public final List<Integer> targetIndexes;

    public DoneCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }


    @Override
    public CommandResult execute() {
        List<String> displayArchivedTasks = new ArrayList<String>();
        Collections.sort(targetIndexes);
        int adjustmentForRemovedTask = 0;
        
        // update history
        doneTasks = new ArrayList<Task>();

        for (int targetIndex: targetIndexes) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();
    
            if (lastShownList.size() < targetIndex - adjustmentForRemovedTask) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
            }
    
            ReadOnlyTask taskToArchive = lastShownList.get(targetIndex - adjustmentForRemovedTask - 1);
            
            model.addDoneTask((Task) taskToArchive);
            // vs new (taskToArchive)?
            // TODO
            
            try {
                model.deleteTask(taskToArchive);
                doneTasks.add((Task) taskToArchive);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            
            
            displayArchivedTasks.add(taskToArchive.toString());
            adjustmentForRemovedTask++;
        }
        
        updateHistory();
        
        String toDisplay = displayArchivedTasks.toString().replace("[", "").replace("]", "");
        return new CommandResult(String.format(MESSAGE_DONE_ITEM_SUCCESS, toDisplay));
    }


    @Override
    public CommandResult undo() {
        doneTasksUndoFail = new ArrayList<Task>();
        
        for (Task doneTask : doneTasks){
            try {
                model.deleteDoneTask(doneTask);
            } catch (TaskNotFoundException e) {
                doneTasksUndoFail.add(doneTask);
            }
        }
        
        model.addTasks(doneTasks);
        return new CommandResult(MESSAGE_DONE_UNDO_SUCCESS);
    }
}
