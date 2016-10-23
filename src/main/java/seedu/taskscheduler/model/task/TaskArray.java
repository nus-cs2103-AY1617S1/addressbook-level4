package seedu.taskscheduler.model.task;

import java.util.ArrayList;

/**
 * Represents the Task array model.
 */

public class TaskArray {

    private final ArrayList<Task> taskList;
    
    public TaskArray() {
        taskList = new ArrayList<Task>();
    }
    
    public void add(Task task) {
        taskList.add(task);
    }

    public Task[] getArray() {
        return taskList.toArray(new Task[taskList.size()]);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList) {
            sb.append("\n");
            sb.append(task.toString());
        }
        return sb.toString();
    }
    
}
