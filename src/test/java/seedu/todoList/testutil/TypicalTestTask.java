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
            //add todo
            ab.addTask(new Todo(new Name("PROJECT 5"), new StartDate("28-11-2016"), new EndDate("29-11-2016"), new Priority("3"), "false"));
            ab.addTask(new Todo(new Name("project 1"), new StartDate("12-12-2016"), new EndDate("13-12-2016"), new Priority("3"), "false"));
            ab.addTask(new Todo(new Name("teambuilding 3"), new StartDate("27-12-2017"), new EndDate("28-12-2017"), new Priority("3"), "false"));
            ab.addTask(new Todo(new Name("assignment 4"), new StartDate("27-10-2017"), new EndDate("28-10-2017"), new Priority("2"), "false"));
            ab.addTask(new Todo(new Name("project 6"), new StartDate("28-10-2017"), new EndDate("29-10-2017"), new Priority("3"), "false"));
            ab.addTask(new Todo(new Name("assignment 6"), new StartDate("28-10-2017"), new EndDate("29-10-2017"), new Priority("2"), "false"));
            
            //Add event
            //ab.addTask(new Event(new Name("e 1"), new StartDate("30-10-2016"), new EndDate("31-10-2016"), new StartTime("0130"), new EndTime("0200"), "false"));
            //ab.addTask(new EventBuilder().withName("e 2").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("false").build());
            //ab.addTask(new EventBuilder().withName("Eeambuilding 3").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build());
            //ab.addTask(new EventBuilder().withName("Essignment 4").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build());
            //ab.addTask(new EventBuilder().withName("Eroject 5").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").withDone("done").build());
            
            //Add deadline
            //ab.addTask(new Deadline(new Name("d 1"), new StartDate("30-10-2017"), new EndTime("1000"), "false"));
            //ab.addTask(new DeadlineBuilder().withName("dd 1").withDate("26-10-2017").withEndTime("1200").build());
            //ab.addTask(new DeadlineBuilder().withName("deambuilding 3").withDate("27-10-2017").withEndTime("1300").build());
            //ab.addTask(new DeadlineBuilder().withName("dssignment 4").withDate("27-10-2017").withEndTime("1400").build());
            //ab.addTask(new DeadlineBuilder().withName("droject 5").withDate("28-10-2017").withEndTime("1500").build());
            
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TestTask[] getTypicaltasks() throws IllegalValueException {
        return new TestTask[]{new TaskBuilder().withName("Todogtt 1").withStartDate("30-10-2017").withEndDate("31-10-2017").withPriority("1").withDone("false").build(), 
                new TaskBuilder().withName("Todogtt 2").withStartDate("26-11-2017").withEndDate("27-11-2017").withPriority("2").withDone("false").build(), 
                new TaskBuilder().withName("Todogtt 3").withStartDate("27-12-2017").withEndDate("28-12-2017").withPriority("3").withDone("false").build(), 
                new TaskBuilder().withName("Todogtt 4").withStartDate("29-12-2017").withEndDate("30-12-2017").withPriority("2").withDone("false").build()};
    }

    public TaskList getTypicalTodoList(){
        TaskList ab = new TaskList();
        loadTodoListWithSampleData(ab);
        return ab;
    }
}
