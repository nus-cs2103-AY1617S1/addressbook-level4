package seedu.todo.guitests;

import org.junit.Test;

import seedu.todo.models.Task;

public class AddCommandTest extends GuiTest {

    @Test
    public void add() {
        Task[] currentList = {};
        Task taskToAdd = new Task();
        assertAddSuccess(taskToAdd, currentList);
    }


    private void assertAddSuccess(Task personToAdd, Task... currentList) {
        // TODO: check Console and TaskList
    }
}
