package seedu.address.history;

import java.util.List;

import seedu.address.model.item.Task;

/**
 * Represents a reversible effect and its details, as a result of a reversible command
 *
 */
public class ReversibleEffect {

    private String commandName;
    
    private List<Task> tasksAffected;
    
    /**
     * Getter method for the name of the command that is associated with this reversible effect
     * @return commandName the name of the command associated with this reversible effect
     */
    public String getCommandName(){
        return commandName;
    }
    
    /**
     * Getter method for the tasks affected in this reversible effect
     * @return tasksAffected the tasks affected by this reversible effect
     */
    public List<Task> getTasksAffected(){
        return tasksAffected;
    }
    
    public ReversibleEffect(String commandName, List<Task> tasksAffected){
        this.commandName = commandName;
        this.tasksAffected = tasksAffected;
    }
}
