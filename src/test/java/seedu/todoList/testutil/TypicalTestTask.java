package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 *
 */
public class TypicalTestTask {

   
    public static TestTask  a1, a2, a3, a4, a5, a6, a7;

    public TypicalTestTask() {
        try {
            a1 =  new TaskBuilder().withTodo("assignment 1").withPriority("1").withStartTime("1400").withEndTime("1600").build();
            a2 = new TaskBuilder().withTodo("project 1").withPriority("2").withStartTime("1100").withEndTime("1200").build();
            a3 = new TaskBuilder().withTodo("teambuilding 3").withPriority("3").withStartTime("1300").withEndTime("1330").build();
            a4 = new TaskBuilder().withTodo("assignment 4").withPriority("4").withStartTime("1400").withEndTime("1430").build();
            a5 = new TaskBuilder().withTodo("project 5").withPriority("5").withStartTime("1500").withEndTime("1530").build();
            //Manually added
            a6 = new TaskBuilder().withTodo("assignment 6").withPriority("2").withStartTime("1600").withEndTime("1630").build();
            a7 = new TaskBuilder().withTodo("homework 7").withPriority("1").withStartTime("1700").withEndTime("1730").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTodoListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Task(a1));
            ab.addTask(new Task(a2));
            ab.addTask(new Task(a3));
            ab.addTask(new Task(a4));
            ab.addTask(new Task(a5));
            ab.addTask(new Task(a6));
            ab.addTask(new Task(a7));
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
