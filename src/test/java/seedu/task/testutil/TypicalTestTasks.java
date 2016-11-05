package seedu.task.testutil;

import java.time.LocalDate;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask  eventWithoutParameter, eventWithLocation, eventWithLocationAndRemarks, 
                            deadlineWithoutParameter, deadlineWithLocation, deadlineWithoutTime,
                            floatWithoutParameter, floatWithLocationAndRemarks, event, deadline, 
                            taskOneToTestFind, taskTwoToTestFind, taskThreeToTestFind;

    //@@author A0138601M
    public TypicalTestTasks() {
        try {
            eventWithoutParameter = new TaskBuilder().withName("Event without parameter")
                    .withInterval(getTodayDate(), "9:30am", getTodayDate(), "12:00pm")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            eventWithLocation = new TaskBuilder().withName("Event with location")
                    .withInterval(getTodayDate(), "13:00", getTodayDate(), "23:59")
                    .withLocation("Office")
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            eventWithLocationAndRemarks = new TaskBuilder().withName("Event with locationAndRemarks")
                    .withInterval("2 jan 2017", "7pm", "3 jan 2017", "8pm")
                    .withLocation("East Coast")
                    .withRemarks("buy present")
                    .withStatus(Status.Type.Incomplete).build();
            deadlineWithoutParameter = new TaskBuilder().withName("Deadline without parameter")
                    .withInterval(null, null, "28 jan 2017", "23:59")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            deadlineWithLocation = new TaskBuilder().withName("Deadline with location")
                    .withInterval(null, null, "28 jan 2017", "12:00pm")
                    .withLocation("Fairprice")
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            deadlineWithoutTime = new TaskBuilder().withName("Deadline without time")
                    .withInterval(null, null, "29 jan 2017", null)
                    .withLocation(null)
                    .withRemarks("graded assignment")
                    .withStatus(Status.Type.Incomplete).build();
            floatWithoutParameter = new TaskBuilder().withName("Float without parameter")
                    .withInterval(null, null, null, null)
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            floatWithLocationAndRemarks = new TaskBuilder().withName("Float with locationAndRemarks")
                    .withInterval(null, null, null, null)
                    .withLocation("school")
                    .withRemarks("graded assignment")
                    .withStatus(Status.Type.Incomplete).build();
          
            //Manually added
            event = new TaskBuilder().withName("ABC project meeting")
                    .withInterval("1/1/2017", "10am", "1 jan 2017", "11:30am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda")
                    .withStatus(Status.Type.Incomplete).build();
            deadline = new TaskBuilder().withName("Go gym")
                    .withInterval(null, null, "2 jan 2017", null)
                    .withLocation(null)
                    .withRemarks("bring towel")
                    .withStatus(Status.Type.Incomplete).build();
            
            //Task for testing FindCommand
            taskOneToTestFind = new TaskBuilder().withName("One two Three")
                    .withInterval(null, null, "24 jan 2017", null)
                    .withLocation(null)
                    .withRemarks("testing")
                    .withStatus(Status.Type.Incomplete).build();
            
            taskTwoToTestFind = new TaskBuilder().withName("one Three Two four")
                    .withInterval(null, null, "27 jan 2017", null)
                    .withLocation("NUS")
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            
            taskThreeToTestFind = new TaskBuilder().withName("Four One three two")
                    .withInterval(null, null, "30 jan 2017", null)
                    .withLocation("home")
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author

    //@@author A0153736B
    private String getTodayDate() {
        LocalDate currentDate = LocalDate.now();
        final StringBuilder sb = new StringBuilder();
        sb.append(currentDate.getDayOfMonth() + "/")
        	.append(currentDate.getMonthValue() + "/")
        	.append(currentDate.getYear());
        return sb.toString();
    }
    //@@author
    
    public static void loadToDoListWithSampleData(ToDoList ab) {

        try {
            ab.addTask(new Task(eventWithoutParameter));
            ab.addTask(new Task(eventWithLocation));
            ab.addTask(new Task(eventWithLocationAndRemarks));
            ab.addTask(new Task(deadlineWithoutParameter));
            ab.addTask(new Task(deadlineWithLocation));
            ab.addTask(new Task(deadlineWithoutTime));
            ab.addTask(new Task(floatWithoutParameter));
            ab.addTask(new Task(floatWithLocationAndRemarks));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{eventWithoutParameter, eventWithLocation, eventWithLocationAndRemarks, 
                deadlineWithoutParameter, deadlineWithLocation, deadlineWithoutTime,
                floatWithoutParameter, floatWithLocationAndRemarks};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
