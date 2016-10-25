package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 *
 */
//@@ Author A0132157M
public class TypicalTestTask {

   
    public static TestTask  a1, a2, a3, a4, a5, a6, a7, a8;

    public TypicalTestTask() {
        try {
            a1 = new TaskBuilder().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("done").build();
            a2 = new TaskBuilder().withName("project 1").withStartDate("26-10-2017").withEndDate("37-10-2017").withPriority("2").withDone("done").build();
            a3 = new TaskBuilder().withName("teambuilding 3").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("3").withDone("done").build();
            a4 = new TaskBuilder().withName("assignment 4").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("2").withDone("done").build();
            a5 = new TaskBuilder().withName("project 5").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("5").withDone("done").build();
            //Manually added
            a6 = new TaskBuilder().withName("assignment 6").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("2").withDone("done").build();
            a7 = new TaskBuilder().withName("homework 7").withStartDate("29-10-2017").withEndDate("30-10-2017").withPriority("1").withDone("done").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTodoListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Todo(a1));
            ab.addTask(new Todo(a2));
            ab.addTask(new Todo(a3));
            ab.addTask(new Todo(a4));
            ab.addTask(new Todo(a5));
            ab.addTask(new Todo(a6));
            ab.addTask(new Todo(a7));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicaltasks() {
        return new TestTask[]{a1, a2, a3, a4, a5, a6, a7};
    }

    public TaskList getTypicalTodoList(){
        TaskList ab = new TaskList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
