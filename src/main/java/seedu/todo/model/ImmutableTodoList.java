package seedu.todo.model;

import java.util.List;

import seedu.todo.model.task.ImmutableTask;

public interface ImmutableTodoList {
    /**
     * Get an immutable list of tasks 
     */
    List<ImmutableTask> getTasks();
}
