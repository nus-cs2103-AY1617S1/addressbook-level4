package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withDescription("buy food").withPriority("high")
                    .withTimeStart("").withTimeEnd("")
                    .build();
            benson = new TaskBuilder().withDescription("finish coding").withPriority("high")
                    .withTimeStart("").withTimeEnd("Sat 23:59")
                    .withTags("homework").build();
            carl = new TaskBuilder().withDescription("bring cake").withPriority("")
                    .withTimeStart("").withTimeEnd("")
                    .withTags("friends", "birthday").build();
            daniel = new TaskBuilder().withDescription("purchase stationery").withPriority("high")
                    .withTimeStart("wed").withTimeEnd("")
                    .build();
            elle = new TaskBuilder().withDescription("play brawl on OW").withPriority("low")
                    .withTimeStart("20:00").withTimeEnd("23:59")
                    .build();
            fiona = new TaskBuilder().withDescription("teach math").withPriority("")
                    .withTimeStart("Sunday").withTimeEnd("")
                    .withTags("student", "OLevels").build();
            george = new TaskBuilder().withDescription("hang out").withPriority("")
                    .withTimeStart("Mon 9:00").withTimeEnd("Tues 10:00")
                    .withTags("friends").build();

            //Manually added
            hoon = new TaskBuilder().withDescription("meeting with boss").withPriority("high")
                    .withTimeStart("14:00").withTimeEnd("")
                    .withTags("critical", "important").build();
            ida = new TaskBuilder().withDescription("check on friend in hospital").withPriority("")
                    .withTimeStart("").withTimeEnd("")
                    .withTags("friends", "recovering").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
