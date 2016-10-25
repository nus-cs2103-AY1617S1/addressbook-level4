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
            event1 = new TaskBuilder().withName("Meeting with Harry").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:45")
                    .withDeadline("Sun Oct 23 21:35:45").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            event2 = new TaskBuilder().withName("Appointment with John").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:46")
                    .withDeadline("Sun Oct 23 21:35:46").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            event3 = new TaskBuilder().withName("Date with Girlfriend").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:47")
                    .withDeadline("Sun Oct 23 21:35:47").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            todo1 = new TaskBuilder().withName("Check email").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:48")
                    .withDeadline("Sun Oct 23 21:35:48").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            todo2 = new TaskBuilder().withName("Consolidate EOY reviews").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:49")
                    .withDeadline("Sun Oct 23 21:35:49").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            todo3 = new TaskBuilder().withName("Purchase new stock of cases").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:50")
                    .withDeadline("Sun Oct 23 21:35:50").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            float1 = new TaskBuilder().withName("Consolidate reports").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:51")
                    .withDeadline("Sun Oct 23 21:35:51").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            float2 = new TaskBuilder().withName("Gym").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:52")
                    .withDeadline("Sun Oct 23 21:35:52").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            float3 = new TaskBuilder().withName("Watch Fixing Good").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:53")
                    .withDeadline("Sun Oct 23 21:35:53").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            
            // Extra for manual addition
            eventExtra = new TaskBuilder().withName("Name EE").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:54")
                    .withDeadline("Sun Oct 23 21:35:54").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            todoExtra = new TaskBuilder().withName("Name TE").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:55")
                    .withDeadline("Sun Oct 23 21:35:55").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
            floatExtra = new TaskBuilder().withName("Name FE").withStartTime("Sun Oct 16 21:35:45").withEndTime("Mon Oct 17 21:35:56")
                    .withDeadline("Sun Oct 23 21:35:56").withRecurrence("Recurrence").withTags("Tag1", "Tag2").build();
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
