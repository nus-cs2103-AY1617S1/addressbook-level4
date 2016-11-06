package seedu.task.testutil;

import java.time.LocalDate;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask  eventWithoutParameter, eventWithLocation, eventWithParameters,
                            deadlineWithLocation, deadlineWithoutTime,
                            floatWithoutParameter, floatWithParameters, 
                            upcomingEvent, overdueDeadline, 
                            taskOneToTestFind, taskTwoToTestFind, taskThreeToTestFind;

    //@@author A0138601M
    public TypicalTestTasks() {
        try {
            eventWithoutParameter = new TaskBuilder().withName("Event without parameter")
                    .withInterval(getTodayDate(), "11:00pm", getTodayDate(), "11:30pm")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            eventWithLocation = new TaskBuilder().withName("Event with location")
                    .withInterval(getTodayDate(), "20:00", getTodayDate(), "23:59")
                    .withLocation("Office")
                    .withRemarks(null)
                    .withStatus(Status.Type.Incomplete).build();
            eventWithParameters = new TaskBuilder().withName("Event with parameters")
                    .withInterval("27/1/2017", "20:00", "27/1/2017", "23:59")
                    .withLocation("Office")
                    .withRemarks("print document")
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
                    .withStatus(Status.Type.Complete).build();
            floatWithParameters = new TaskBuilder().withName("Float with parameters")
                    .withInterval(null, null, null, null)
                    .withLocation("school")
                    .withRemarks("graded assignment")
                    .withStatus(Status.Type.Overdue).build();
          
            //Manually added
            upcomingEvent = new TaskBuilder().withName("Upcoming event")
                    .withInterval("1/1/2017", "10am", "1 jan 2017", "11:30am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda")
                    .withStatus(Status.Type.Incomplete).build();
            overdueDeadline = new TaskBuilder().withName("Overdue deadline")
                    .withInterval(null, null, "30 oct 2016", "1pm")
                    .withLocation(null)
                    .withRemarks("bring towel")
                    .withStatus(Status.Type.Overdue).build();
            
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
            ab.addTask(new Task(eventWithParameters));
            ab.addTask(new Task(deadlineWithLocation));
            ab.addTask(new Task(deadlineWithoutTime));
            ab.addTask(new Task(floatWithoutParameter));
            ab.addTask(new Task(floatWithParameters));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{eventWithoutParameter, eventWithLocation, eventWithParameters, 
                deadlineWithLocation, deadlineWithoutTime, floatWithoutParameter, floatWithParameters};
    }

    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
