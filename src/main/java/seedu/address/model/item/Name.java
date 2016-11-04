package seedu.address.model.item;

//@@author A0139655U
/**
 * Represents a Task's name in the task manager.
 * Guarantees: immutable
 */
public class Name {

    /** Name of key in map that maps to the task name of task */
    private static final String MAP_TASK_NAME_KEY = "taskName";
    
    private String taskName;
    
    public Name(String name) {
        assert name != null;
        name = name.trim();
        this.taskName = name;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    /** 
     * @return the key in map that maps to the task name of task
     */
    public static String getMapNameKey() {
        return MAP_TASK_NAME_KEY;
    }

    @Override
    public String toString() {
        return this.taskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.taskName.equals(((Name) other).taskName)); // state check
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

    public int compareTo(Name other) {
        return taskName.compareTo(other.taskName);
    }
}
