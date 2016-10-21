package seedu.oneline.testutil;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.*;

/**
 *
 */
public class TypicalTestTasks {  

    public static TestTask event1, event2, event3, eventExtra,
                            todo1, todo2, todo3, todoExtra,
                            float1, float2, float3, floatExtra;

    static {
        initTestTasks();
    }
    
    
    private static void initTestTasks() {
        try {
            event1 = new TaskBuilder().withName("Meeting with Harry").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            event2 = new TaskBuilder().withName("Appointment with John").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            event3 = new TaskBuilder().withName("Date with Girlfriend").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            todo1 = new TaskBuilder().withName("Check email").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            todo2 = new TaskBuilder().withName("Consolidate EOY reviews").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            todo3 = new TaskBuilder().withName("Purchase new stock of cases").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            float1 = new TaskBuilder().withName("Consolidate reports").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            float2 = new TaskBuilder().withName("Gym").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            float3 = new TaskBuilder().withName("Watch Fixing Good").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            
            // Extra for manual addition
            eventExtra = new TaskBuilder().withName("Name EE").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            todoExtra = new TaskBuilder().withName("Name TE").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
            floatExtra = new TaskBuilder().withName("Name FE").withStartTime("Start Time").withEndTime("End Time")
                    .withDeadline("Deadline").withRecurrence("Recurrence").withTags("Tag1").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(event1));
            ab.addTask(new Task(event2));
            ab.addTask(new Task(event3));
            ab.addTask(new Task(todo1));
            ab.addTask(new Task(todo2));
            ab.addTask(new Task(todo3));
            ab.addTask(new Task(float1));
            ab.addTask(new Task(float2));
            ab.addTask(new Task(float3));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{event1, event2, event3, todo1, todo2, todo3, float1, float2, float3};
    }

    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}
