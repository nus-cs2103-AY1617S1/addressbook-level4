package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.*;
import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 *
 */
//@@author A0132157M reused
public class TypicalTestDeadline {

   
    public static TestDeadline  d1, d2, d3, d4, d5, d6, d7, d8;

    public TypicalTestDeadline() {
/*        try {
            d1 = new DeadlineBuilder().withName("d 1").withDate("30-10-2017").withEndTime("1000").withDone("ND").build();
            d2 = new DeadlineBuilder().withName("dd 1").withDate("26-10-2017").withEndTime("1200").build();
            d3 = new DeadlineBuilder().withName("deambuilding 3").withDate("27-10-2017").withEndTime("1300").build();
            d4 = new DeadlineBuilder().withName("deambuilding 3").withDate("27-10-2017").withEndTime("1300").build();
            d5 = new DeadlineBuilder().withName("droject 5").withDate("28-10-2017").withEndTime("1500").build();
            //Manually added
            d6 = new DeadlineBuilder().withName("dssignment 6").withDate("28-10-2017").withEndTime("1600").build();
            d7 = new DeadlineBuilder().withName("domework 7").withDate("29-10-2017").withEndTime("1700").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }*/
    }

    public static void loadDeadlineListWithSampleData(TaskList ab) {

/*        try {
            ab.addTask(new Deadline(d1));
            ab.addTask(new Deadline(d2));
            ab.addTask(new Deadline(d3));
            ab.addTask(new Deadline(d4));
            ab.addTask(new Deadline(d5));
            ab.addTask(new Deadline(d6));
            ab.addTask(new Deadline(d7));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            assert false : "not possible";
        }*/
    }

    public TestDeadline[] getTypicalDeadline() throws IllegalValueException {
        return new TestDeadline[]{
                new DeadlineBuilder().withName("deadlinegtt 1").withStartDate("30-11-2017").withEndTime("10:00").withDone("false").build(),
                new DeadlineBuilder().withName("deadlinegtt 2").withStartDate("26-11-2017").withEndTime("12:00").withDone("false").build(),
                new DeadlineBuilder().withName("deadlinegtt 3").withStartDate("27-11-2017").withEndTime("13:00").withDone("false").build(),
                new DeadlineBuilder().withName("deadlinegtt 4").withStartDate("27-11-2017").withEndTime("13:00").withDone("false").build(),
                new DeadlineBuilder().withName("deadlinegtt 5").withStartDate("28-11-2017").withEndTime("15:00").withDone("false").build()};
    }

    public TaskList getTypicalDeadlineList(){
        TaskList ab = new TaskList();
        loadDeadlineListWithSampleData(ab);
        return ab;
    }

}
