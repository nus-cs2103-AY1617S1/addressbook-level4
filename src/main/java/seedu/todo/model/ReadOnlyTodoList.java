package seedu.todo.model;

import com.google.common.collect.ImmutableList;

import seedu.todo.model.task.ReadOnlyTask;

public interface ReadOnlyTodoList {
    /**
     * Get a list of tasks 
     */
    public ImmutableList<ReadOnlyTask> getTasks();
}
