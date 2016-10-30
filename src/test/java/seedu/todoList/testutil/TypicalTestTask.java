package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 *
 */
//@@author A0132157M reused
public class TypicalTestTask {

   
    public static  TestTask  a1;
    public static TestTask a2;
    public static TestTask a3;
    public static TestTask a4;
    public static TestTask a5;
    public static TestTask a6;
    public static TestTask a7;
    
    static { initTestTasks(); }

    private static void initTestTasks() {
        try {
            a1 = new TaskBuilder().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build();
            a2 = new TaskBuilder().withName("project 1").withStartDate("26-10-2017").withEndDate("27-10-2017").withPriority("2").withDone("false").build();
            a3 = new TaskBuilder().withName("teambuilding 3").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("3").withDone("false").build();
            a4 = new TaskBuilder().withName("assignment 4").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("2").withDone("false").build();
            a5 = new TaskBuilder().withName("project 5").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("3").withDone("false").build();
            //Manually added
            a6 = new TaskBuilder().withName("assignment 6").withStartDate("28-10-2017").withEndDate("29-10-2017").withPriority("2").withDone("false").build();
            a7 = new TaskBuilder().withName("homework 7").withStartDate("29-10-2017").withEndDate("30-10-2017").withPriority("1").withDone("false").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTodoListWithSampleData(TaskList ab) {

//        try {
//            //add todo
//            ab.addTask(new Todo(a1));
////            ab.addTask(new TestTask().withName("assignment 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build());
////            ab.addTask(new TaskBuilder().withName("project 1").withStartDate("26-10-2017").withEndDate("27-10-2017").withPriority("2").withDone("false").build());
////            ab.addTask(new TaskBuilder().withName("teambuilding 3").withStartDate("27-10-2017").withEndDate("28-10-2017").withPriority("3").withDone("false").build());
//            //Add event
//            //ab.addTask(new Event(new Name("e 1"), new StartDate("30-10-2016"), new EndDate("31-10-2016"), new StartTime("0130"), new EndTime("0200"), "false"));
//
//            //Add deadline
//            //ab.addTask(new Deadline(new Name("d 1"), new StartDate("30-10-2017"), new EndTime("1000"), "false"));
//
//        } catch (UniqueTaskList.DuplicatetaskException e) {
//            assert false : "not possible";
//        }
    }

    public TestTask[] getTypicaltasks() throws IllegalValueException {
        return new TestTask[]{};}
    
                //new TaskBuilder().withName("Todogtt 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build(), 
                //new TaskBuilder().withName("Todogtt 2").withStartDate("26-11-2017").withEndDate("27-11-2017").withPriority("2").withDone("false").build(), 
                //new TaskBuilder().withName("Todogtt 3").withStartDate("27-12-2017").withEndDate("28-12-2017").withPriority("3").withDone("false").build(), 
                //new TaskBuilder().withName("Todogtt 4").withStartDate("29-12-2017").withEndDate("30-12-2017").withPriority("2").withDone("false").build()};
    

    public TaskList getTypicalTodoList(){
        TaskList ab = new TaskList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
