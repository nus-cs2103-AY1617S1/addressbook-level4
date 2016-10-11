package seedu.todo.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;

public class TestTodoList implements ImmutableTodoList {

    @Override
    public List<ImmutableTask> getTasks() {
        Task task1 = new Task("test1");
        task1.setStartTime(LocalDateTime.now());
        task1.setEndTime(LocalDateTime.now().plusDays(1l));

        List<ImmutableTask> listOfTasks = new ArrayList<>();
        listOfTasks.add(task1);

        return listOfTasks;
    }

}
