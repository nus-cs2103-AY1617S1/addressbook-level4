package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.address.history.ReversibleEffect;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.Task;

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
                model.deleteTasks(readOnlyTasksAffected);
                // TODO: inform user if task is not found and so not deleted if necessary, exception somewhere..
                return new CommandResult("Redid last undo command:\n\t" + commandName + " " + firstAffectedTask);
            
            
            case "edit":
                assert tasksAffected.size() == 2;
                
                // this is the updated task before edit
                Task prevStateOfEditedTask = getSecondTaskInList(tasksAffected);
                
                // keep a deep copy for printing since the task will be changed
                Task copyOfEditedTask = new Task(prevStateOfEditedTask);
                
                Task editedTaskToRevert = firstAffectedTask;
                                
                redoEditCommand(prevStateOfEditedTask, editedTaskToRevert);
                return new CommandResult("Redid last command:\n\t" + commandName + " " + copyOfEditedTask + " reverted back to " + prevStateOfEditedTask);  
            
            case "clear":
                model.deleteTasks(readOnlyTasksAffected);
                return new CommandResult("Redid last command:\n\t" + commandName);
            
            
            case "done":
                model.addDoneTasks(tasksAffected);
                model.deleteTasks(readOnlyTasksAffected);
                return new CommandResult("Undid last command:\n\t" + commandName);
                
            default:
                return new CommandResult("Nothing to redo.");
        }
        
        
    }
    
    private void redoEditCommand(Task prevStateOfEditedTask, Task editedTaskToRevert) {
        // temporary method of undoing edit by editing back all fields 
        // until there is a single unified edit method
        
        Name oldTaskName = prevStateOfEditedTask.getName();
        Optional<Date> oldStartDate = prevStateOfEditedTask.getStartDate();
        Optional<Date> oldEndDate = prevStateOfEditedTask.getEndDate();
        Priority oldPriority = prevStateOfEditedTask.getPriorityValue();
        Optional<RecurrenceRate> oldReccurence = prevStateOfEditedTask.getRecurrenceRate();
       
        Task taskInListToRevert = model.getTaskManager().getUniqueUndoneTaskList().getTask(editedTaskToRevert);
        
        model.editName(taskInListToRevert, oldTaskName);
        
        model.editPriority(taskInListToRevert, oldPriority);
        
        // edit back the start date
        if (oldStartDate.isPresent()){
            model.editStartDate(taskInListToRevert, oldStartDate.get());
        }
        else {
            model.editStartDate(taskInListToRevert, null);
        }
        
        // edit back the end date
        if (oldEndDate.isPresent()){
            model.editEndDate(taskInListToRevert, oldEndDate.get());
        }
        else {
            model.editEndDate(taskInListToRevert, null);
        }
        
        // edit back the recurrence rate
        if (oldReccurence.isPresent()){
            model.editRecurrence(taskInListToRevert, oldReccurence.get());
        }
        else{
            model.editRecurrence(taskInListToRevert, null);
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
