package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.history.ReversibleEffect;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the last undo command, reversing the effect on the task manager.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "redo";
    

    public RedoCommand() {
    }

    @Override
    public CommandResult execute() {
        assert history != null;
        
        // if we are at the latest state where there is no later 'undo commands' to redo, return nothing to redo
        if (history.isLatest()){
            return new CommandResult("Nothing to redo.");
        }
        
        ReversibleEffect reversibleEffect = history.redoStep();
        String commandName = reversibleEffect.getCommandName();
        List<Task> tasksAffected = reversibleEffect.getTasksAffected();
        List<ReadOnlyTask> readOnlyTasksAffected = convertTaskListToReadOnlyTaskList(tasksAffected);
        
        assert tasksAffected.size() > 0 && readOnlyTasksAffected.size() > 0;
        Task firstAffectedTask = getFirstTaskInList(tasksAffected);
        
        switch(commandName){
            case "add":
                model.addTask(firstAffectedTask);
                return new CommandResult("Redid last undo command:\n\t" + commandName + " " + firstAffectedTask);
                
            case "delete":
                try {
                    model.deleteTask(firstAffectedTask);
                } catch (TaskNotFoundException e) {
                    // TODO Auto-generated catch block
                    return new CommandResult("Unable to redo the delete command that was undid.");
                }
                return new CommandResult("Redid last undo command:\n\t" + commandName + " " + firstAffectedTask);
            
            /*
            case "edit":
                assert tasksAffected.size() == 2;
                
                // this is the updated task
                Task secondTaskInList = getSecondTaskInList(tasksAffected);
                
                // replace the updated task (find by index) with the old, unedited task
                model.
                
                break;
            */
            
            case "clear":
                model.deleteTasks(readOnlyTasksAffected);
                return new CommandResult("Undid last command:\n\t" + commandName);
            
            /*
            case "done":
                break;
            */
                
            default:
                return new CommandResult("Nothing to undo.");
        }
        
        
    }
    
    private List<ReadOnlyTask> convertTaskListToReadOnlyTaskList(List<Task> tasks){
        List<ReadOnlyTask> readOnlyTaskList = new ArrayList<ReadOnlyTask>();
        for (Task task: tasks){
            readOnlyTaskList.add(task);
        }
        return readOnlyTaskList;
    }
    
    private Task getFirstTaskInList(List<Task> tasks){
        assert tasks.size() >= 1;
        return tasks.get(0);
    }
    
    private Task getSecondTaskInList(List<Task> tasks){
        assert tasks.size() >= 2;
        return tasks.get(1);
    }
    
}
