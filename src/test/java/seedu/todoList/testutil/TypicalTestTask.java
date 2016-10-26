package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 *
 */
//@@ Author A0132157M
public class TypicalTestTask {

   
    public static TestTask  a1, a2, a3, a4, a5, a6, a7;

    public TypicalTestTask() {
        /*try {
            //a1 = new TaskBuilder().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build();
            //a2 = new TaskBuilder().withName("project 1").withStartDate("26-10-2017").withEndDate("27-10-2017").withPriority("2").withDone("false").build();
            //a3 = new TaskBuilder().withName("teambuilding 3").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("3").withDone("false").build();
            //a4 = new TaskBuilder().withName("assignment 4").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("2").withDone("false").build();
            //a5 = new TaskBuilder().withName("project 5").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("3").withDone("false").build();
            //Manually added
            //a6 = new TaskBuilder().withName("assignment 6").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("2").withDone("false").build();
            //a7 = new TaskBuilder().withName("homework 7").withStartDate("29-10-2017").withEndDate("30-10-2017").withPriority("1").withDone("false").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }*/
    }

    public static void loadTodoListWithSampleData(TaskList ab) {

        try {
            ab.addTask(new Todo(new Name("PPP 1"), new StartDate("26-10-2017"), new EndDate("27-10-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 2"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 3"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 7"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 4"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 5"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("todo 6"), new StartDate("11-11-2017"), new EndDate("12-11-2017"), new Priority("2"), "false"));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TestTask[] getTypicaltasks() throws IllegalValueException {
        return new TestTask[]{new TaskBuilder().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build(), 
                new TaskBuilder().withName("project 1").withStartDate("26-11-2017").withEndDate("27-11-2017").withPriority("2").withDone("false").build(), 
                new TaskBuilder().withName("teambuilding 3").withStartDate("27-12-2017").withEndDate("28-12-2017").withPriority("3").withDone("false").build(), 
                new TaskBuilder().withName("assignment 4").withStartDate("29-12-2017").withEndDate("30-12-2017").withPriority("2").withDone("false").build()};
    }

    public TaskList getTypicalTodoList(){
        TaskList ab = new TaskList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
