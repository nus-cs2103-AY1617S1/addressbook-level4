package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.TaskMan;
import seedu.taskman.model.event.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask taskCS2101, taskCS2103T, taskCS2309, taskCS3244, taskCS2105, taskCS2106, taskCS2107, taskCS2102, taskCS2104;

    public TypicalTestTasks() {
        try {
            taskCS2101 =  new TaskBuilder().withTitle("CS2101").withFrequency("3d").withDeadline("mon 1200")
                    .withTags("User Guide").build();
            taskCS2103T = new TaskBuilder().withTitle("CS2103T").withFrequency("1w").withDeadline("wed 1000")
                    .withTags("Super Shag", "V0.2").build();
            taskCS2309 = new TaskBuilder().withTitle("CS2309").withDeadline("fri 0900").withFrequency("1w").withSchedule("fri 0000 to fri 0300").build();
            taskCS3244 = new TaskBuilder().withTitle("CS3244").withDeadline("thu 1400").withFrequency("1w").withSchedule("thu 1400 to thu 1500").build();
            taskCS2105 = new TaskBuilder().withTitle("CS2105").withDeadline("mon 1600").withFrequency("1w").withSchedule("mon 1400, mon 1600").build();
            taskCS2106 = new TaskBuilder().withTitle("CS2106").withDeadline("fri 1200").withFrequency("1y").withSchedule("fri 1200, fri 1400").build();
            taskCS2107 = new TaskBuilder().withTitle("CS2107").withDeadline("fri 1800").withFrequency("2y").withSchedule("fri 1600, sat 1600").build();

            //Manually added
            taskCS2102 = new TaskBuilder().withTitle("CS2102").withDeadline("tue 1200").withFrequency("2d").withSchedule("tue 1000, tue 1200").build();
            taskCS2104 = new TaskBuilder().withTitle("CS2104").withDeadline("mon 1000").withFrequency("1d").withSchedule("sun 2300 to mon 0100").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManWithSampleData(TaskMan ab) {

        try {
            ab.addTask(new Task(taskCS2101));
            ab.addTask(new Task(taskCS2103T));
            ab.addTask(new Task(taskCS2309));
            ab.addTask(new Task(taskCS3244));
            ab.addTask(new Task(taskCS2105));
            ab.addTask(new Task(taskCS2106));
            ab.addTask(new Task(taskCS2107));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{taskCS2101, taskCS2103T, taskCS2309, taskCS3244, taskCS2105, taskCS2106, taskCS2107};
    }

    public TaskMan getTypicalTaskMan(){
        TaskMan ab = new TaskMan();
        loadTaskManWithSampleData(ab);
        return ab;
    }
}
