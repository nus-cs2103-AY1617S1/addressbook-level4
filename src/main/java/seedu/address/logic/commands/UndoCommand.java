package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the last reversible command, reversing the effect on the task manager.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "undo";
    

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        assert history != null;
        
        // if we are at the earliest state where there is no earlier reversible command to undo, return nothing to undo
        if (history.isEarliestCommand()){
            return new CommandResult("Nothing to undo.");
        }
        
        UndoableCommand cmdToUndo = history.undoStep();
        return cmdToUndo.undo();
        
        
        /*
        ReversibleEffect reversibleEffect = history.undoStep();
        String commandName = reversibleEffect.getCommandName();
        List<Task> tasksAffected = reversibleEffect.getTasksAffected();
        List<ReadOnlyTask> readOnlyTasksAffected = convertTaskListToReadOnlyTaskList(tasksAffected);
        
        assert tasksAffected.size() > 0 && readOnlyTasksAffected.size() > 0;
        Task firstAffectedTask = getFirstTaskInList(tasksAffected);
        
        switch(commandName){
            case "add":
                try {
                    model.deleteTask(firstAffectedTask);
                } catch (TaskNotFoundException e) {
                    // TODO Auto-generated catch block
                    return new CommandResult("Unable to undo last add command.");
                }
                return new CommandResult("Undid last command:\n\t" + commandName + " " + firstAffectedTask);
                
            case "delete":
                model.addTasks(tasksAffected);
                return new CommandResult("Undid last command:\n\t" + commandName + " " + firstAffectedTask);
            
            
            case "edit":
                assert tasksAffected.size() == 2;
                
                // this is the updated task
                Task editedTaskToRevert = getSecondTaskInList(tasksAffected);
                
                // keep a deep copy for printing since the task will be changed
                Task copyOfEditedTask = new Task(editedTaskToRevert);
                
                Task prevStateOfEditedTask = firstAffectedTask;
                                
                undoEditCommand(prevStateOfEditedTask, editedTaskToRevert);
                return new CommandResult("Undid last command:\n\t" + commandName + " " + copyOfEditedTask + " reverted back to " + prevStateOfEditedTask);    
            
            case "clear":
                model.addTasks(tasksAffected);
                return new CommandResult("Undid last command:\n\t" + commandName);
            
            
            case "done":
                model.deleteDoneTasks(readOnlyTasksAffected);
                model.addTasks(tasksAffected);
                return new CommandResult("Undid last command:\n\t" + commandName);
            
                
            default:
                return new CommandResult("Nothing to undo.");
        }
        */
        
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
