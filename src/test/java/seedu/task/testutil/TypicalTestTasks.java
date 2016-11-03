package seedu.task.testutil;

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
                    .withInterval("1/11/2016", "9:30am", "1/11/2016", "12:00pm")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            eventWithLocation = new TaskBuilder().withName("Event with location")
                    .withInterval("2 nov 2016", "13:00", "2 nov 2016", "23:59")
                    .withLocation("Office")
                    .withRemarks(null)
                    .withStatus(false).build();
            eventWithLocationAndRemarks = new TaskBuilder().withName("Event with locationAndRemarks")
                    .withInterval("2 dec 2016", "7pm", "3 dec 2016", "8pm")
                    .withLocation("East Coast")
                    .withRemarks("buy present")
                    .withStatus(false).build();
            deadlineWithoutParameter = new TaskBuilder().withName("Deadline without parameter")
                    .withInterval(null, null, "28 dec 2016", "23:59")
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            deadlineWithLocation = new TaskBuilder().withName("Deadline with location")
                    .withInterval(null, null, "28 dec 2016", "12:00pm")
                    .withLocation("Fairprice")
                    .withRemarks(null)
                    .withStatus(false).build();
            deadlineWithoutTime = new TaskBuilder().withName("Deadline without time")
                    .withInterval(null, null, "29 dec 2016", null)
                    .withLocation(null)
                    .withRemarks("graded assignment")
                    .withStatus(false).build();
            floatWithoutParameter = new TaskBuilder().withName("Float without parameter")
                    .withInterval(null, null, null, null)
                    .withLocation(null)
                    .withRemarks(null)
                    .withStatus(false).build();
            floatWithLocationAndRemarks = new TaskBuilder().withName("Float with locationAndRemarks")
                    .withInterval(null, null, null, null)
                    .withLocation("school")
                    .withRemarks("graded assignment")
                    .withStatus(false).build();
          
            //Manually added
            event = new TaskBuilder().withName("ABC project meeting")
                    .withInterval("1/12/2016", "10am", "1 dec 2016", "11:30am")
                    .withLocation("Orchard")
                    .withRemarks("prepare agenda")
                    .withStatus(false).build();
            deadline = new TaskBuilder().withName("Go gym")
                    .withInterval(null, null, "2 dec 2016", null)
                    .withLocation(null)
                    .withRemarks("bring towel")
                    .withStatus(false).build();
            
            //Task for testing FindCommand
            taskOneToTestFind = new TaskBuilder().withName("One two Three")
                    .withInterval(null, null, "24 dec 2016", null)
                    .withLocation(null)
                    .withRemarks("testing")
                    .withStatus(false).build();
            
            taskTwoToTestFind = new TaskBuilder().withName("one Three Two four")
                    .withInterval(null, null, "27 dec 2016", null)
                    .withLocation("NUS")
                    .withRemarks(null)
                    .withStatus(false).build();
            
            taskThreeToTestFind = new TaskBuilder().withName("Four One three two")
                    .withInterval(null, null, "30 dec 2016", null)
                    .withLocation("home")
                    .withRemarks(null)
                    .withStatus(false).build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
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
