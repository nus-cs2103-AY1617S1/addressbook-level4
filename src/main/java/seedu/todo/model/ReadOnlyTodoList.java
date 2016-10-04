package seedu.todo.model;

import java.util.List;

import seedu.todo.model.task.ReadOnlyTask;

public interface ReadOnlyTodoList {
    /**
     * Get an immutable list of tasks 
     */
    public List<ReadOnlyTask> getTasks();
}
