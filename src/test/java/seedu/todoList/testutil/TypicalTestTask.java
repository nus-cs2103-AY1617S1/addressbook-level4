package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 *
 */
public class TypicalTestTask {

   
    public static TestTask  a1, a2, a3, a4, a5, a6, a7, a8;

    public TypicalTestTask() {
        try {
            a1 = new TaskBuilder().withName("assignment 1").withDate("30-10-2016").withPriority("1").build();
            a2 = new TaskBuilder().withName("project 1").withDate("26-10-2016").withPriority("2").build();
            a3 = new TaskBuilder().withName("teambuilding 3").withDate("27-10-2016").withPriority("3").build();
            a4 = new TaskBuilder().withName("assignment 4").withDate("27-10-2016").withPriority("2").build();
            a5 = new TaskBuilder().withName("project 5").withDate("28-10-2016").withPriority("5").build();
            //Manually added
            a6 = new TaskBuilder().withName("assignment 6").withDate("28-10-2016").withPriority("2").build();
            a7 = new TaskBuilder().withName("homework 7").withDate("29-10-2016").withPriority("1").build();

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
